package ign.geoip.models;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.maxmind.geoip.DatabaseInfo;
import com.maxmind.geoip.LookupService;

import java.io.File;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
*/

/**
 * User: cpatni
 * Date: Aug 9, 2010
 * Time: 11:00:31 PM
 */

@Singleton
public class AutoUpdateScheduler {
    

    private volatile long lastModified;
    private volatile long lastCheckpoint;
    private ScheduledExecutorService scheduledExecutorService;
    private GeoIPCity geoIPCity;
    private String database;
    private Provider<LookupService> lookupServiceProvider;
    private int blackoutPeriod = 5;  //in seconds



    private static final Logger logger = Logger.getLogger(AutoUpdateScheduler.class.getName());



    @Inject
    public AutoUpdateScheduler(@Named("database") String database, GeoIPCity geoIPCity, Provider<LookupService> lookupServiceProvider) {
        this.database = database;
        this.geoIPCity = geoIPCity;
        this.lookupServiceProvider = lookupServiceProvider;
        this.lastModified = new File(database).lastModified();
    }


    @Inject
    public void schedule(final ScheduledExecutorService scheduledExecutorService) {
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                try {
                    long lastModifiedTime = new File(database).lastModified();
                    //if database is modified within five minutes then
                    //we instate a blackout period and try again
                    if(lastModifiedTime > System.currentTimeMillis() - getBlackoutPeriod() *1000L) {
                        logger.config("AutoUpdateScheduler.run geodb recently modified. Observing blackout period of "+2*getBlackoutPeriod()+" secs.");
                        scheduledExecutorService.schedule(this, 2*getBlackoutPeriod(), TimeUnit.SECONDS);
                    }
                    else {
                        if(lastModifiedTime == lastModified) {
                            logger.config("Reloading db: "+database+", last modified at "+new Date(lastModifiedTime));
                            LookupService oldService = geoIPCity.setLookupService(lookupServiceProvider.get());
                            lastModified = lastModifiedTime;


                            if(oldService != null) {
                                logger.config("closing db last modified at "+new Date(lastModified));
                                oldService.close();
                            }
                        }

                    }
                    lastCheckpoint = System.currentTimeMillis();

                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error while refreshing the db. ", e);
                }
            }
        }, 0, 15, TimeUnit.SECONDS);
    }

    
    public int getBlackoutPeriod() {
        return blackoutPeriod;
    }

    public void setBlackoutPeriod(int blackoutPeriod) {
        this.blackoutPeriod = blackoutPeriod;
    }

    public GeoIPCity getGeoIPCity() {
        return geoIPCity;
    }

    public long getLastModified() {
        return lastModified;
    }

    public long getLastCheckpoint() {
        return lastCheckpoint;
    }

    public String getDatabase() {
        return database;
    }

    public DatabaseInfo getDatabaseInfo() {
        return getGeoIPCity().getLookupService().getDatabaseInfo();
    }
}
