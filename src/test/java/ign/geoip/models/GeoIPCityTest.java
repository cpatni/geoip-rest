package ign.geoip.models;

import com.google.inject.Provider;
import com.maxmind.geoip.LookupService;
import org.junit.Test;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

/**
 * User: cpatni
 * Date: Aug 8, 2010
 * Time: 10:50:07 PM
 */
public class GeoIPCityTest {

    @Test
    public void countryLookup() {

        Country turkey = new Country("TR", "Turkey");
        String ip = "45.12.97.23";

        com.maxmind.geoip.Location stubLocation = new com.maxmind.geoip.Location();
        stubLocation.countryCode = "TR";
        stubLocation.countryName = "Turkey";

        Provider requestp = createMock(Provider.class);
        expect(requestp.get()).andReturn(null);

        LookupService mockLookupService = createMock(LookupService.class);
        expect(mockLookupService.getLocation(ip)).andReturn(stubLocation);

        replay(mockLookupService, requestp);

        GeoIPCity geoip = new GeoIPCity(mockLookupService, requestp);
        Country result = geoip.country(ip);
        assertEquals(turkey.getCode(), result.getCode());
        assertEquals(turkey.getName(), result.getName());
    }

    @Test
    public void cityLookup() {

        City mystrySpot = CityTest.mysterySpot();

        String ip = "45.12.97.23";


        com.maxmind.geoip.Location stubLocation = new com.maxmind.geoip.Location();
        stubLocation.countryCode = "US";
        stubLocation.countryName = "United States";
        stubLocation.region = "CA";
        stubLocation.city = "Santa Cruz";
        stubLocation.postalCode = "95065";
        stubLocation.longitude = -122.02f;
        stubLocation.latitude = 36.58f;
        stubLocation.area_code = 831;
        stubLocation.metro_code = 807;

        Provider requestp = createMock(Provider.class);
        expect(requestp.get()).andReturn(null);

        LookupService mockLookupService = createMock(LookupService.class);
        expect(mockLookupService.getLocation(ip)).andReturn(stubLocation);

        replay(mockLookupService, requestp);

        GeoIPCity geoip = new GeoIPCity(mockLookupService, requestp);
        City result = geoip.location(ip);
        assertEquals(mystrySpot.getCountryCode(), result.getCountryCode());
        assertEquals(mystrySpot.getCountryName(), result.getCountryName());
        assertEquals(mystrySpot.getRegionCode(), result.getRegionCode());
        assertEquals(mystrySpot.getRegionName(), result.getRegionName());
        assertEquals(mystrySpot.getName(), result.getName());
        assertEquals(mystrySpot.getPostalCode(), result.getPostalCode());
        assertEquals(mystrySpot.getLongitude(), result.getLongitude(), 0.1);
        assertEquals(mystrySpot.getLatitude(), result.getLatitude(), 0.1);
        assertEquals(mystrySpot.getAreaCode(), result.getAreaCode());
        assertEquals(mystrySpot.getMetroCode(), result.getMetroCode());
        assertEquals(mystrySpot.getMetroName(), result.getMetroName());
        assertEquals(mystrySpot.getTimeZone(), result.getTimeZone());
    }
}
