package ign.geoip.boot;

import com.sun.jersey.api.core.DefaultResourceConfig;
import ign.geoip.controllers.AboutController;
import ign.geoip.controllers.CountriesController;
import ign.geoip.controllers.CitiesController;

import javax.ws.rs.core.MediaType;

/**
 * User: cpatni
 * Date: Aug 8, 2010
 * Time: 10:00:37 AM
 */
public class GeoIPApplication extends DefaultResourceConfig {
    public GeoIPApplication() {
        super(CitiesController.class, CountriesController.class, AboutController.class);
        this.getMediaTypeMappings().put("html", MediaType.TEXT_HTML_TYPE);
        this.getMediaTypeMappings().put("xml", MediaType.APPLICATION_XML_TYPE);
        this.getMediaTypeMappings().put("atom", MediaType.APPLICATION_ATOM_XML_TYPE);

        this.getMediaTypeMappings().put("json", MediaType.APPLICATION_JSON_TYPE);
        this.getMediaTypeMappings().put("js", new MediaType("application", "x-javascript"));
        this.getMediaTypeMappings().put("jsonp", new MediaType("application", "x-javascript"));

        this.getMediaTypeMappings().put("rb", new MediaType("text", "ruby"));
        this.getMediaTypeMappings().put("php", new MediaType("text", "php"));

        this.getMediaTypeMappings().put("jpg", new MediaType("image", "jpeg"));
        this.getMediaTypeMappings().put("jpeg", new MediaType("image", "jpeg"));
        this.getMediaTypeMappings().put("png", new MediaType("image", "png"));
    }

}
