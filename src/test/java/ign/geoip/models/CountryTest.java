package ign.geoip.models;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: cpatni
 * Date: Aug 8, 2010
 * Time: 9:39:41 PM
 */
public class CountryTest {

    static Country turkey() {
        return new Country("TR", "Turkey");
    }

    @Test
    public void countryXml() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<country>\n" +
                "  <code>TR</code>\n" +
                "  <name>Turkey</name>\n" +
                "</country>";
        assertEquals(xml, turkey().toXml());

    }

    @Test
    public void countryJson() {
        assertEquals("{\"code\": \"TR\", \"name\": \"Turkey\"}", turkey().toJson());

    }

    @Test
    public void countryJsonp() {
        assertEquals("foo({\"code\": \"TR\", \"name\": \"Turkey\"})", turkey().toJsonp("foo"));

    }

    @Test
    public void countryRuby() {
        assertEquals("{:code => \"TR\", :name => \"Turkey\"}", turkey().toRuby());

    }

    @Test
    public void countryPhp() {
        assertEquals("array(\"code\" => \"TR\", \"name\" => \"Turkey\")", turkey().toPhp());

    }


    @Test
    public void countryLegacyJson() {
        assertEquals("( {\"class\":\"com.ign.geo.Country\", \"code\": \"TR\", \"name\": \"Turkey\"} )", turkey().toLegacyJson());

    }

}
