package ign.geoip.controllers;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import ign.geoip.models.GeoIPCity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * User: cpatni
 * Date: Aug 8, 2010
 * Time: 2:32:58 PM
 */

@RequestScoped
@Path("/cities/{ip}")
public class CitiesController {
    private GeoIPCity geoIP;


    @Inject
    public CitiesController(GeoIPCity geoIP) {
        this.geoIP = geoIP;
    }


    @GET
    public String locationHtml(@PathParam("ip") String ip) {
        return geoIP.location(ip).toXml();
    }

    @Produces(MediaType.APPLICATION_XML)
    @GET
    public String locationXml(@PathParam("ip") String ip) {
        return geoIP.location(ip).toXml();

    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String locationJson(@PathParam("ip") String ip) {
        return geoIP.location(ip).toJson();
    }

    @GET
    @Produces({"application/x-javascript"})
    public String locationJs(@PathParam("ip") String ip, @QueryParam("callback") String callback) {
        //return new JSONWithPadding();
        return geoIP.location(ip).toJsonp(callback);
    }

    @Produces("text/ruby")
    @GET
    public String locationRuby(@PathParam("ip") String ip) {
        return geoIP.location(ip).toRuby();

    }

    @Produces("text/php")
    @GET
    public String locationPhp(@PathParam("ip") String ip) {
        return geoIP.location(ip).toPhp();
    }
    
}
