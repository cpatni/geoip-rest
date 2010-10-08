package ign.geoip.models;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.maxmind.geoip.*;
import com.sun.jersey.api.NotFoundException;

import javax.servlet.http.HttpServletRequest;

import static ign.geoip.helpers.ApplicationHelper.getIP;

/**
 * User: cpatni
 * Date: Aug 8, 2010
 * Time: 2:08:34 PM
 */
@Singleton
public class GeoIPCity {

    private volatile LookupService lookup;
    private Provider<HttpServletRequest> requestp;
    private Metros metros;

    @Inject
    public GeoIPCity(LookupService lookup, Metros metros, Provider<HttpServletRequest> requestp) {
        this.lookup = lookup;
        this.metros = metros;
        this.requestp = requestp;
    }

    public LookupService getLookupService() {
        return lookup;
    }

    public LookupService setLookupService(LookupService lookup) {
        LookupService old = this.lookup;
        this.lookup = lookup;
        return old == lookup ? null : old;
    }

    public Country country(String ip) {
        ip = getIP(ip, requestp.get());
        com.maxmind.geoip.Location location = lookup.getLocation(ip);
        if(location == null) {
            throw new NotFoundException("There is no place like "+ip);
        }
        return new Country(location.countryCode, location.countryName);
    }


    public City location(String ip) {
        String effectiveIP = getIP(ip, requestp.get());
        com.maxmind.geoip.Location loc = lookup.getLocation(effectiveIP);
        if(loc == null) {
            throw new NotFoundException("There is no place like "+ip);
        }
        return new City(loc.countryCode, loc.countryName, loc.region,
                regionName.regionNameByCode(loc.countryCode, loc.region), loc.city, loc.postalCode,
                loc.longitude, loc.latitude, loc.area_code, loc.metro_code, metros.find(loc.metro_code),
                timeZone.timeZoneByCountryAndRegion(loc.countryCode, loc.region));
    }

    public DatabaseInfo getDatabaseInfo() {
        return lookup.getDatabaseInfo();
    }

}
