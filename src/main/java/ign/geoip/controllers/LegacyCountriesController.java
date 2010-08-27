package ign.geoip.controllers;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import com.sun.jersey.api.NotFoundException;
import ign.geoip.models.Country;
import ign.geoip.models.GeoIPCity;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * geo.ign.com/g/?c=1&fmt=json&ip=212.101.97.206
 * geo.ign.com/g/?c=1&fmt=xml&ip=212.101.97.206
 * User: cpatni
 * Date: Aug 8, 2010
 * Time: 11:28:58 AM
 */
@RequestScoped
@Path("/g")
public class LegacyCountriesController {
    private GeoIPCity geoIP;


    @Inject
    public LegacyCountriesController(GeoIPCity geoIP) {
        this.geoIP = geoIP;
    }

    @GET
    public Response legacyCountry(@QueryParam("c") String c, @QueryParam("fmt") String fmt, @QueryParam("ip") String ip) {
        Country country = null;
        try {
            country = geoIP.country(ip);
        } catch (NotFoundException e) {
            country = Country.UNKNOWN;
        }
        if ("xml".equalsIgnoreCase(fmt)) {
            return Response.ok(country.toXml(), MediaType.APPLICATION_XML_TYPE).build();
        } else {//if("json".equalsIgnoreCase(fmt)) {
            return Response.ok(country.toLegacyJson(), MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

}
