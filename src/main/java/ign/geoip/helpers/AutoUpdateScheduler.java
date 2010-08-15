package ign.geoip.helpers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.maxmind.geoip.DatabaseInfo;
import com.maxmind.geoip.LookupService;
import ign.geoip.models.GeoIPCity;

import java.io.File;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
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


    volatile long lastModified;
    private volatile long lastCheckpoint;
    private GeoIPCity geoIPCity;
    private File database;
    private Provider<LookupService> lookupServiceProvider;
    private int blackoutPeriod = 2;  //in minutes
    private int period = 24;  //in hours

    Logger logger = Logger.getLogger(AutoUpdateScheduler.class.getName());
    ScheduledFuture<?> scheduledFuture;

    @Inject
    public AutoUpdateScheduler(@Named("database") File database, GeoIPCity geoIPCity, Provider<LookupService> lookupServiceProvider) {
        this.database = database;
        this.geoIPCity = geoIPCity;
        this.lookupServiceProvider = lookupServiceProvider;
        this.lastModified = database.lastModified();
    }


    @Inject
    public ScheduledFuture<?> schedule(final ScheduledExecutorService scheduledExecutorService) {
        scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(buildTryUpdateIfRequiredTask(scheduledExecutorService), 0, getPeriod(), TimeUnit.HOURS);
        return scheduledFuture;
    }

    Runnable buildTryUpdateIfRequiredTask(final ScheduledExecutorService scheduledExecutorService) {
        return new Runnable() {
            public void run() {
                tryUpdateIfRequired(scheduledExecutorService, this);
            }

        };
    }

    void tryUpdateIfRequired(ScheduledExecutorService scheduledExecutorService, Runnable tryUpdateIfRequiredTask) {
        try {
            long lastModifiedTime = database.lastModified();
            //if database is modified within five minutes then
            //we instate a blackout period and try again
            if (shouldObserveBlackoutPeriod(lastModifiedTime)) {
                logger.config("AutoUpdateScheduler.run geodb recently modified. Observing blackout period of " + getBlackoutPeriod() + " secs.");
                scheduledExecutorService.schedule(tryUpdateIfRequiredTask, getBlackoutPeriod(), TimeUnit.MINUTES);
            } else if (shouldReload(lastModifiedTime)) {
                reload();
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error while refreshing the db", e);
        }
        finally {
            lastCheckpoint = System.currentTimeMillis();
        }
    }


    void reload() {
        long lastModifiedTime = database.lastModified();
        logger.config("Reloading db: " + database + ", last modified at " + new Date(lastModifiedTime));
        LookupService oldService = geoIPCity.setLookupService(lookupServiceProvider.get());
        if (oldService != null) {
            logger.config("closing db last modified at " + new Date(lastModified));
            oldService.close();
        }
        lastModified = lastModifiedTime;
    }

    boolean shouldReload(long lastModifiedTime) {
        return lastModifiedTime != lastModified;
    }

    boolean shouldObserveBlackoutPeriod(long lastModifiedTime) {
        return lastModifiedTime > System.currentTimeMillis() - getBlackoutPeriod() * 60 * 1000L;
    }

    public ScheduledFuture<?> getScheduledFuture() {
        return scheduledFuture;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
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

    public long getNextCheckpoint() {
        return System.currentTimeMillis() + scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
    }

    public File getDatabase() {
        return database;
    }
}
