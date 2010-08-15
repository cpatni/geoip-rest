package ign.geoip.boot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import com.google.inject.name.Named;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.RequestScoped;
import com.maxmind.geoip.LookupService;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import ign.geoip.controllers.CountriesController;
import ign.geoip.controllers.SendServerHeader;
import ign.geoip.controllers.TrafficController;

import javax.servlet.ServletContextEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

/**
 * User: cpatni
 * Date: Aug 7, 2010
 * Time: 9:51:21 PM
 */
public class Bootstrap extends GuiceServletContextListener {

    private LookupService lookupService;
    private ScheduledExecutorService scheduledExecutorService;


    @Override
    protected Injector getInjector() {

        return Guice.createInjector(getStage(), new JerseyServletModule() {

            @Override
            protected void configureServlets() {
                bind(CountriesController.class).in(RequestScoped.class);

                filter("/*").through(SendServerHeader.class);

                Map<String, String> params = new HashMap<String, String>();
                params.put("javax.ws.rs.Application", GeoIPApplication.class.getName());
                serve("/servlet/SimpleServlet").with(TrafficController.class);
                serve("/traffic").with(TrafficController.class);
                serve("/hc").with(TrafficController.class);
                serve("/*").with(GuiceContainer.class, params);
            }

            @Provides
            public LookupService createLookupService() throws IOException {
                lookupService = new LookupService(getDatabase(), LookupService.GEOIP_MEMORY_CACHE);
                return lookupService;
            }

            @Provides
            @Singleton
            public ScheduledExecutorService createScheduledExecutorService() throws IOException {
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                return scheduledExecutorService;
            }

            @Provides
            @Named("database")
            public String getDatabase() {
                return System.getProperty("database", "db/GeoIPCity.dat");
            }

            @Provides
            @Named("database")
            public File getDatabaseFile() {
                return new File(System.getProperty("database", "db/GeoIPCity.dat"));
            }

            @Provides
            @Named("metros")
            public String getAllMetrosAsString() throws IOException {
                InputStream in = getClass().getClassLoader().getResourceAsStream("ign/geoip/models/metros.tsv");
                try {
                    StringBuilder fileData = new StringBuilder(1000);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    char[] buf = new char[1024];
                    int numRead=0;
                    while((numRead=reader.read(buf)) != -1){
                        String readData = String.valueOf(buf, 0, numRead);
                        fileData.append(readData);
                    }
                    return fileData.toString();

                }
                finally {
                    in.close();
                }
            }

        });

    }

    public Stage getStage() {
        return Stage.valueOf(getEnvironment().toUpperCase());
    }

    public String getEnvironment() {
        return System.getProperty("environment", "development");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        super.contextInitialized(servletContextEvent);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);
        if(scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
        }
        if(lookupService != null) {
            lookupService.close();
        }
    }


}
