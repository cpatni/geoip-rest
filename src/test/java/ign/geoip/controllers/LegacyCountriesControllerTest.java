package ign.geoip.controllers;

import com.maxmind.geoip.LookupService;
import ign.geoip.models.Country;
import ign.geoip.models.GeoIPCity;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

/**
 * User: cpatni
 * Date: Aug 9, 2010
 * Time: 1:08:56 PM
 */
public class LegacyCountriesControllerTest {

    private Country turkey = new Country("TR", "Turkey");
    private String ip = "45.12.97.23";


    private LegacyCountriesController subject() {
        GeoIPCity geoIPCity = createMock(GeoIPCity.class);
        expect(geoIPCity.country(ip)).andReturn(turkey).anyTimes();
        replay(geoIPCity);
        return new LegacyCountriesController(geoIPCity);
    }


    @Test
    public void testLegacyCountryXml() {
        Response response = subject().legacyCountry("1", "xml", ip);
        assertEquals(200, response.getStatus());
        assertEquals(turkey.toXml(), response.getEntity());
    }

    @Test
    public void testLegacyCountryJson() {
        Response response = subject().legacyCountry("1", "json", ip);
        assertEquals(200, response.getStatus());
        assertEquals(turkey.toLegacyJson(), response.getEntity());


    }
}
