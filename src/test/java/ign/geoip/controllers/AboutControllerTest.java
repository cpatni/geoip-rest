package ign.geoip.controllers;

import com.google.inject.util.Providers;
import com.maxmind.geoip.DatabaseInfo;
import ign.geoip.helpers.AutoUpdateScheduler;
import ign.geoip.models.GeoIPCity;
import org.junit.Test;

import java.io.File;
import java.util.Date;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;

/**
 * User: cpatni
 * Date: Aug 12, 2010
 * Time: 10:11:29 AM
 */
public class AboutControllerTest {
    @Test
    public void testIndex() throws Exception {

        Date now = new Date();
        DatabaseInfo info = createMock(DatabaseInfo.class);
        expect(info.getDate()).andReturn(now).anyTimes();
        expect(info.isPremium()).andReturn(true).anyTimes();

        AutoUpdateScheduler scheduler = createMock(AutoUpdateScheduler.class);
        GeoIPCity geoIPCity = createMock(GeoIPCity.class);
        expect(scheduler.getLastModified()).andReturn(100L).anyTimes();
        expect(scheduler.getLastCheckpoint()).andReturn(80L).anyTimes();
        expect(scheduler.getNextCheckpoint()).andReturn(280L).anyTimes();
        expect(scheduler.getDatabase()).andReturn(new File("/")).anyTimes();
        expect(scheduler.getGeoIPCity()).andReturn(geoIPCity).anyTimes();
        expect(geoIPCity.getDatabaseInfo()).andReturn(info).anyTimes();
        replay(info, scheduler, geoIPCity);
        AboutController subject = new AboutController(geoIPCity, Providers.of(scheduler));
        String result = subject.index();
        assertEquals(new File("/"), scheduler.getDatabase());
        assertEquals(now, info.getDate());
        assertEquals(true, info.isPremium());
        assertEquals(100L, scheduler.getLastModified());
        assertEquals(80L, scheduler.getLastCheckpoint());
        assertTrue(result.indexOf("Refresh pending") > 0);

    }
}
