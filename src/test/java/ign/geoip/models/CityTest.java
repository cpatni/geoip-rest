package ign.geoip.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: cpatni
 * Date: Aug 8, 2010
 * Time: 9:49:58 PM
 */
public class CityTest {

    public static City mysterySpot() {
        return new City("US",
                "United States",
                "CA", "California", "Santa Cruz", "95065",
                -122.02f, 36.58f, 831, 807, "San Francisco-Oakland-San Jose", "America/Los_Angeles"
        );
    }

    @Test
    public void locationXml() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<city>\n" +
                "  <name>Santa Cruz</name>\n" +
                "  <region_code>CA</region_code>\n" +
                "  <region_name>California</region_name>\n" +
                "  <country_code>US</country_code>\n" +
                "  <country_name>United States</country_name>\n" +
                "  <postal_code>95065</postal_code>\n" +
                "  <metro_code>807</metro_code>\n" +
                "  <metro_name>San Francisco-Oakland-San Jose</metro_name>\n" +
                "  <time_zone>America/Los_Angeles</time_zone>\n" +
                "  <area_code>831</area_code>\n" +
                "  <longitude>-122.02</longitude>\n" +
                "  <latitude>36.58</latitude>\n" +
                "</city>";
        assertEquals(xml, mysterySpot().toXml());

    }

    @Test
    public void locationJson() {
        String json = "{\"name\": \"Santa Cruz\", \"region_code\": \"CA\", \"region_name\": \"California\", \"country_code\": \"US\", \"country_name\": \"United States\", \"postal_code\": \"95065\", \"metro_code\": 807, \"metro_name\": \"San Francisco-Oakland-San Jose\", \"time_zone\": \"America\\/Los_Angeles\", \"area_code\": 831, \"longitude\": -122.02, \"latitude\": 36.58}";
        assertEquals(json, mysterySpot().toJson());

    }

    @Test
    public void locationJsonp() {
        String jsonp = "foo({\"name\": \"Santa Cruz\", \"region_code\": \"CA\", \"region_name\": \"California\", \"country_code\": \"US\", \"country_name\": \"United States\", \"postal_code\": \"95065\", \"metro_code\": 807, \"metro_name\": \"San Francisco-Oakland-San Jose\", \"time_zone\": \"America\\/Los_Angeles\", \"area_code\": 831, \"longitude\": -122.02, \"latitude\": 36.58})";
        assertEquals(jsonp, mysterySpot().toJsonp("foo"));

    }

    @Test
    public void locationRuby() {
        String ruby = "{:name => \"Santa Cruz\", :region_code => \"CA\", :region_name => \"California\", :country_code => \"US\", :country_name => \"United States\", :postal_code => \"95065\", :metro_code => 807, :metro_name => \"San Francisco-Oakland-San Jose\", :time_zone => \"America/Los_Angeles\", :area_code => 831, :longitude => -122.02, :latitude => 36.58}";
        assertEquals(ruby, mysterySpot().toRuby());

    }

    @Test
    public void locationPhp() {
        String php = "array(\"name\" => \"Santa Cruz\", \"region_code\" => \"CA\", \"region_name\" => \"California\", \"country_code\" => \"US\", \"country_name\" => \"United States\", \"postal_code\" => \"95065\", \"metro_code\" => 807, \"metro_name\" => \"San Francisco-Oakland-San Jose\", \"time_zone\" => \"America/Los_Angeles\", \"area_code\" => 831, \"longitude\" => -122.02, \"latitude\" => 36.58)";
        assertEquals(php, mysterySpot().toPhp());

    }

}
