package ign.geoip.controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.servlet.RequestScoped;
import com.maxmind.geoip.DatabaseInfo;
import ign.geoip.models.AutoUpdateScheduler;
import ign.geoip.models.GeoIPCity;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.Date;

/**
 * User: cpatni
 * Date: Aug 10, 2010
 * Time: 8:19:34 AM
 */
@RequestScoped
@Path("/about")
public class AboutController {
    //we inject the provider here so that AutoUpdateScheduler is injected lazily in dev mode
    private Provider<AutoUpdateScheduler> autoUpdateSchedulerProvider;

    @Inject
    public AboutController(Provider<AutoUpdateScheduler> autoUpdateSchedulerProvider) {
        this.autoUpdateSchedulerProvider = autoUpdateSchedulerProvider;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String index() {
        AutoUpdateScheduler scheduler = autoUpdateSchedulerProvider.get();
        DatabaseInfo databaseInfo = scheduler.getGeoIPCity().getLookupService().getDatabaseInfo();
        return "<html><body>" +
                "<dl>" +
                "<dt>Database</dt><dd>"+scheduler.getDatabase() + " ("+new File(scheduler.getDatabase()).getAbsolutePath() +")</dd>"+
                "<dt>Date</dt><dd>"+databaseInfo.getDate() +"</dd>"+
                "<dt>Premium</dt><dd>"+databaseInfo.isPremium() +"</dd>"+
                "<dt>Last Modified</dt><dd>"+new Date(scheduler.getLastModified()) +"</dd>"+
                "<dt>Last Checked</dt><dd>"+new Date(scheduler.getLastCheckpoint()) +"</dd>"+
                "<dt>Refresh pending</dt><dd>"+(new File(scheduler.getDatabase()).lastModified() > scheduler.getLastCheckpoint()) +"</dd>"+
                "</body></html>";


    }
}
