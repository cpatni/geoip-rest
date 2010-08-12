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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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


        });

    }

    //ses.scheduleWithFixedDelay(this, 0, 1, TimeUnit.DAYS);


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
        scheduledExecutorService.shutdownNow();
        lookupService.close();
    }


}
