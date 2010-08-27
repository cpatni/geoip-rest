package ign.geoip.controllers;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import ign.geoip.models.Country;
import ign.geoip.models.GeoIPCity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: cpatni
 * Date: Aug 7, 2010
 * Time: 9:56:19 PM
 */

@RequestScoped
@Path("/countries/{ip}")
public class CountriesController {

    private GeoIPCity geoIP;


    @Inject
    public CountriesController(GeoIPCity geoIP) {
        this.geoIP = geoIP;
    }

    @GET
    public String countryHtml(@PathParam("ip") String ip) {
        Country country = geoIP.country(ip);
        
        return country.toXml();
    }

    @Produces(MediaType.APPLICATION_XML)
    @GET
    public String countryXml(@PathParam("ip") String ip) {
        return geoIP.country(ip).toXml();

    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String countryJson(@PathParam("ip") String ip) {
        return geoIP.country(ip).toJson();
    }

    @GET
    @Produces({"application/x-javascript"})
    public String countryJs(@PathParam("ip") String ip, @QueryParam("callback") String callback) {
        //return new JSONWithPadding();
        return geoIP.country(ip).toJsonp(callback);
    }

    @Produces("text/ruby")
    @GET
    public String countryRuby(@PathParam("ip") String ip) {
        return geoIP.country(ip).toRuby();

    }

    @Produces("text/php")
    @GET
    public String countryPhp(@PathParam("ip") String ip) {
        return geoIP.country(ip).toPhp();
    }

}
