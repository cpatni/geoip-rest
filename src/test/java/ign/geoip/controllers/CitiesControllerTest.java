package ign.geoip.controllers;

import ign.geoip.models.CityTest;
import ign.geoip.models.GeoIPCity;
import ign.geoip.models.City;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

/**
 * User: cpatni
 * Date: Aug 9, 2010
 * Time: 8:35:09 PM
 */
public class CitiesControllerTest {
    private City mysterySpot = CityTest.mysterySpot();

    private String ip = "45.12.97.23";


    private CitiesController subject() {
        GeoIPCity geoIPCity = createMock(GeoIPCity.class);
        expect(geoIPCity.location(ip)).andReturn(mysterySpot).anyTimes();
        replay(geoIPCity);
        return new CitiesController(geoIPCity);
    }


    @Test
    public void testLocationHtml() throws Exception {
        String result = subject().locationHtml(ip);
        assertEquals(mysterySpot.toXml(), result);
    }

    @Test
    public void testLocationXml() throws Exception {
        String result = subject().locationXml(ip);
        assertEquals(mysterySpot.toXml(), result);
    }

    @Test
    public void testLocationJson() throws Exception {
        String result = subject().locationJson(ip);
        assertEquals(mysterySpot.toJson(), result);
    }

    @Test
    public void testLocationJs() throws Exception {
        String result = subject().locationJs(ip, "foo");
        assertEquals(mysterySpot.toJsonp("foo"), result);
    }

    @Test
    public void testLocationRuby() throws Exception {
        String result = subject().locationRuby(ip);
        assertEquals(mysterySpot.toRuby(), result);
    }

    @Test
    public void testLocationPhp() throws Exception {
        String result = subject().locationPhp(ip);
        assertEquals(mysterySpot.toPhp(), result);
    }
}
