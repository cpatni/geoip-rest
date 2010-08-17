package ign.geoip.helpers;

import com.google.inject.Provider;
import com.maxmind.geoip.LookupService;
import ign.geoip.models.GeoIPCity;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: cpatni
 * Date: Aug 13, 2010
 * Time: 11:57:10 PM
 */
public class AutoUpdateSchedulerTest {
    @Test
    public void testSchedule() throws Exception {
        final Runnable r = createMock(Runnable.class);

        final long now = System.currentTimeMillis();
        File file = createMock(File.class);
        expect(file.lastModified()).andReturn(now - 3600 * 1000L).anyTimes();

        ScheduledExecutorService scheduledExecutorService = createMock(ScheduledExecutorService.class);
        ScheduledFuture scheduledFuture = createMock(ScheduledFuture.class);
        expect(scheduledExecutorService.scheduleWithFixedDelay(r, 0, 12, TimeUnit.HOURS)).andReturn(scheduledFuture);


        replay(file, scheduledExecutorService);
        AutoUpdateScheduler aus = new AutoUpdateScheduler(file, null, null) {
            @Override
            Runnable buildTryUpdateIfRequiredTask(ScheduledExecutorService scheduledExecutorService) {
                return r;
            }
        };

        aus.setPeriod(12);
        ScheduledFuture<?> sfr = aus.schedule(scheduledExecutorService);
        assertEquals(scheduledFuture, sfr);
        assertEquals(scheduledFuture, aus.getScheduledFuture());
    }

    @Test
    public void shouldReloadTest() {
        File file = createMock(File.class);
        expect(file.lastModified()).andReturn(1000L).anyTimes();
        replay(file);
        AutoUpdateScheduler aus = new AutoUpdateScheduler(file, null, null);
        assertFalse(aus.shouldReload(1000L));
        assertTrue(aus.shouldReload(2000L));
    }


    @Test
    public void shouldObserveBlackoutPeriod() {
        final long now = System.currentTimeMillis();
        File file = createMock(File.class);
        expect(file.lastModified()).andReturn(now - 3600 * 1000L).anyTimes();
        replay(file);

        AutoUpdateScheduler aus = new AutoUpdateScheduler(file, null, null);
        aus.setBlackoutPeriod(2);

        assertFalse(aus.shouldObserveBlackoutPeriod(now - 10 * 60 * 1000L));
        assertTrue(aus.shouldObserveBlackoutPeriod(now - 60 * 1000L));
    }


    @Test
    public void reloadTest() {
        final long now = System.currentTimeMillis();
        File file = createMock(File.class);
        expect(file.lastModified()).andReturn(now - 3600 * 1000L);
        expect(file.lastModified()).andReturn(now - 1800 * 1000L);


        LookupService oldLookupServiceMock = createMock(LookupService.class);
        oldLookupServiceMock.close();
        LookupService newLookupServiceMock = createMock(LookupService.class);
        //Provider lookupServiceProvider = createMock(Provider.class);

        @SuppressWarnings({"unchecked"})
        Provider<LookupService> lookupServiceProvider = createMock(Provider.class);
        expect(lookupServiceProvider.get()).andReturn(oldLookupServiceMock);
        expect(lookupServiceProvider.get()).andReturn(newLookupServiceMock);

        GeoIPCity geoIPCityStub = new GeoIPCity(oldLookupServiceMock, null, null);
        expect(geoIPCityStub.setLookupService(newLookupServiceMock)).andReturn(oldLookupServiceMock);

        replay(file, lookupServiceProvider, oldLookupServiceMock);

        AutoUpdateScheduler aus = new AutoUpdateScheduler(file, geoIPCityStub, lookupServiceProvider);
        assertEquals(aus.getLastModified(), now - 3600 * 1000L);
        aus.reload();
        assertEquals(aus.getLastModified(), now - 1800 * 1000L);
    }

    @Test
    public void tryUpdateIfRequiredObserveBlackout() {
        Runnable r = createMock(Runnable.class);

        final long now = System.currentTimeMillis();
        File file = createMock(File.class);
        expect(file.lastModified()).andReturn(now - 3600 * 1000L).anyTimes();

        ScheduledExecutorService scheduledExecutorService = createMock(ScheduledExecutorService.class);
        expect(scheduledExecutorService.schedule(r, 2, TimeUnit.MINUTES)).andReturn(null);


        replay(file, scheduledExecutorService);

        AutoUpdateScheduler aus = new AutoUpdateScheduler(file, null, null) {
            @Override
            boolean shouldObserveBlackoutPeriod(long lastModifiedTime) {
                return true;
            }
        };

        aus.setBlackoutPeriod(2);
        assertEquals(0, aus.getLastCheckpoint());

        aus.tryUpdateIfRequired(scheduledExecutorService, r);
        assertTrue(aus.getLastCheckpoint() > 0);
    }

    @Test
    public void tryUpdateIfRequiredReload() {

        final long now = System.currentTimeMillis();
        File file = createMock(File.class);
        expect(file.lastModified()).andReturn(now - 3600 * 1000L).anyTimes();

        replay(file);

        AutoUpdateScheduler aus = new AutoUpdateScheduler(file, null, null) {
            @Override
            boolean shouldObserveBlackoutPeriod(long lastModifiedTime) {
                return false;
            }

            @Override
            boolean shouldReload(long lastModifiedTime) {
                return true;
            }

            @Override
            void reload() {
                lastModified = now;
            }
        };

        assertEquals(0, aus.getLastCheckpoint());
        aus.tryUpdateIfRequired(null, null);
        assertTrue(aus.getLastCheckpoint() > 0);
        assertEquals(now, aus.getLastModified());
    }

    @Test
    public void tryUpdateIfRequiredException() {
        final RuntimeException e = new RuntimeException("Exception for tryUpdateIfRequiredException test");
        Logger logger = createMock(Logger.class);
        logger.log(Level.WARNING, "Error while refreshing the db", e);

        final long now = System.currentTimeMillis();
        File file = createMock(File.class);
        expect(file.lastModified()).andReturn(now - 3600 * 1000L).anyTimes();

        replay(file, logger);

        AutoUpdateScheduler aus = new AutoUpdateScheduler(file, null, null) {
            @Override
            boolean shouldObserveBlackoutPeriod(long lastModifiedTime) {
                return false;
            }

            @Override
            boolean shouldReload(long lastModifiedTime) {
                return true;
            }

            @Override
            void reload() {
                throw e;
            }

        };

        aus.logger = logger;
        assertEquals(0, aus.getLastCheckpoint());
        aus.tryUpdateIfRequired(null, null);
        assertEquals(now - 3600 * 1000L, aus.getLastModified());
        assertTrue(aus.getLastCheckpoint() > 0);
    }

    @Test
    public void buildTryUpdateIfRequiredTaskTest() {
        final long now = System.currentTimeMillis();
        File file = createMock(File.class);
        expect(file.lastModified()).andReturn(now - 3600 * 1000L).anyTimes();


        replay(file);

        AutoUpdateScheduler aus = new AutoUpdateScheduler(file, null, null) {
            @Override
            void tryUpdateIfRequired(ScheduledExecutorService scheduledExecutorService, Runnable tryUpdateIfRequiredTask) {
                super.lastModified = now;

            }

        };

        ScheduledExecutorService scheduledExecutorService = createMock(ScheduledExecutorService.class);
        Runnable task = aus.buildTryUpdateIfRequiredTask(scheduledExecutorService);
        task.run();
        assertEquals(now, aus.getLastModified());


    }

    @Test
    public void getGeoIPCityTest() {
        final long now = System.currentTimeMillis();
        File file = createMock(File.class);
        expect(file.lastModified()).andReturn(now - 3600 * 1000L).anyTimes();
        GeoIPCity geoIPCity = createMock(GeoIPCity.class);
        replay(file);
        AutoUpdateScheduler aus = new AutoUpdateScheduler(file, geoIPCity, null);
        assertEquals(geoIPCity, aus.getGeoIPCity());
    }

    @Test
    public void getDatabaseTest() {
        final long now = System.currentTimeMillis();
        File file = createMock(File.class);
        expect(file.lastModified()).andReturn(now - 3600 * 1000L).anyTimes();
        replay(file);
        AutoUpdateScheduler aus = new AutoUpdateScheduler(file, null, null);
        assertEquals(file, aus.getDatabase());
    }

    @Test
    public void getNextCheckpointTest() {
        final long now = System.currentTimeMillis();
        File file = createMock(File.class);
        expect(file.lastModified()).andReturn(now - 3600 * 1000L).anyTimes();

        ScheduledFuture scheduledFuture = createMock(ScheduledFuture.class);
        expect(scheduledFuture.getDelay(TimeUnit.MILLISECONDS)).andReturn(12*3600*1000L);
        replay(file, scheduledFuture);
        AutoUpdateScheduler aus = new AutoUpdateScheduler(file, null, null);
        aus.scheduledFuture = scheduledFuture;
        assertTrue("Get Next Checkpoint should be around 12 hour in future", aus.getNextCheckpoint() > System.currentTimeMillis() + 10*3600*1000L);
    }


}
