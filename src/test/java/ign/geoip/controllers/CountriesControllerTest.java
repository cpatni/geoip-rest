package ign.geoip.controllers;

import ign.geoip.models.Country;
import ign.geoip.models.GeoIPCity;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

/**
 * User: cpatni
 * Date: Aug 9, 2010
 * Time: 8:36:46 PM
 */
public class CountriesControllerTest {
    private Country turkey = new Country("TR", "Turkey");
    private String ip = "45.12.97.23";


    private CountriesController subject() {
        GeoIPCity geoIPCity = createMock(GeoIPCity.class);
        expect(geoIPCity.country(ip)).andReturn(turkey).anyTimes();
        replay(geoIPCity);
        return new CountriesController(geoIPCity);
    }


    @Test
    public void testCountryHtml() throws Exception {
        String result = subject().countryHtml(ip);
        assertEquals(turkey.toXml(), result);
    }

    @Test
    public void testCountryXml() throws Exception {
        String result = subject().countryXml(ip);
        assertEquals(turkey.toXml(), result);
    }

    @Test
    public void testCountryJson() throws Exception {
        String result = subject().countryJson(ip);
        assertEquals(turkey.toJson(), result);
    }

    @Test
    public void testCountryJs() throws Exception {
        String result = subject().countryJs(ip, "foo");
        assertEquals(turkey.toJsonp("foo"), result);
    }

    @Test
    public void testCountryRuby() throws Exception {
        String result = subject().countryRuby(ip);
        assertEquals(turkey.toRuby(), result);
    }

    @Test
    public void testCountryPhp() throws Exception {
        String result = subject().countryPhp(ip);
        assertEquals(turkey.toPhp(), result);
    }
}
