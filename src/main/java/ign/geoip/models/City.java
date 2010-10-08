package ign.geoip.models;

import ign.geoip.helpers.ApplicationHelper;

import static ign.geoip.helpers.ApplicationHelper.isTrivial;
import static org.apache.commons.lang.StringEscapeUtils.escapeJava;
import static org.apache.commons.lang.StringEscapeUtils.escapeJavaScript;
import static org.apache.commons.lang.StringEscapeUtils.escapeXml;


/**
 * User: cpatni
 * Date: Aug 7, 2010
 * Time: 11:06:37 AM
 */
public class City {
    private String countryCode;
    private String countryName;
    private String regionCode;
    private String regionName;
    private String name;
    private String postalCode;
    private float longitude;
    private float latitude;
    private int areaCode;
    private int metroCode;
    private String metroName;
    private String timeZone;

    public City(String countryCode, String countryName,
                String regionCode, String regionName,
                String name, String postalCode, float longitude, float latitude,
                int areaCode, int metroCode, String metroName, String timeZone) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.regionCode = regionCode;
        this.regionName = regionName;
        this.name = name;
        this.postalCode = postalCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.areaCode = areaCode;
        this.metroCode = metroCode;
        this.metroName = metroName;
        this.timeZone = timeZone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getName() {
        return name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public int getMetroCode() {
        return metroCode;
    }

    public String getMetroName() {
        return metroName;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String toXml() {
        StringBuilder sb = new StringBuilder().append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n")
                .append("<city>\n")
                .append("  <name>").append(escapeXml(name)).append("</name>\n")
                .append("  <region-code>").append(escapeXml(regionCode)).append("</region-code>\n")
                .append("  <region-name>").append(escapeXml(regionName)).append("</region-name>\n")
                .append("  <country-code>").append(escapeXml(countryCode)).append("</country-code>\n")
                .append("  <country-name>").append(escapeXml(countryName)).append("</country-name>\n")
                .append("  <postal-code>").append(escapeXml(postalCode)).append("</postal-code>\n")
                .append("  <metro-code>").append(metroCode).append("</metro-code>\n")
                .append("  <metro-name>").append(escapeXml(metroName)).append("</metro-name>\n")
                .append("  <time-zone>").append(escapeXml(timeZone)).append("</time-zone>\n")
                .append("  <area-code>").append(areaCode).append("</area-code>\n")
                .append("  <longitude>").append(longitude).append("</longitude>\n")
                .append("  <latitude>").append(latitude).append("</latitude>\n")
                .append("</city>");
        return sb.toString();
    }

    static String jsStringLiteral(String s) {
        return isTrivial(s) ? "null" : "\"" + escapeJavaScript(s) + "\"";
    }

    static String rbStringLiteral(String s) {
        return isTrivial(s) ? "nil" : "\"" + escapeJava(s) + "\"";
    }
    static String phpStringLiteral(String s) {
        return isTrivial(s) ? "null" : "\"" + escapeJava(s) + "\"";
    }


    public String toJson() {
        StringBuilder sb = new StringBuilder()
                .append("{")
                .append("\"name\": ").append(jsStringLiteral(name)).append(", ")
                .append("\"regionCode\": ").append(jsStringLiteral(regionCode)).append(", ")
                .append("\"regionName\": ").append(jsStringLiteral(regionName)).append(", ")
                .append("\"countryCode\": ").append(jsStringLiteral(countryCode)).append(", ")
                .append("\"countryName\": ").append(jsStringLiteral(countryName)).append(", ")
                .append("\"postalCode\": ").append(jsStringLiteral(postalCode)).append(", ")
                .append("\"metroCode\": ").append(metroCode).append(", ")
                .append("\"metroName\": ").append(jsStringLiteral(metroName)).append(", ")
                .append("\"timeZone\": ").append(jsStringLiteral(timeZone)).append(", ")
                .append("\"areaCode\": ").append(areaCode).append(", ")
                .append("\"longitude\": ").append(longitude).append(", ")
                .append("\"latitude\": ").append(latitude)
                .append("}");
        return sb.toString();
    }

    public String toJsonp(String callback) {
        return (isTrivial(callback) ? "" : callback) + "(" + toJson() + ")";


    }


    public String toRuby() {
        StringBuilder sb = new StringBuilder()
                .append("{")
                .append(":name => ").append(rbStringLiteral(name)).append(", ")
                .append(":region_code => ").append(rbStringLiteral(regionCode)).append(", ")
                .append(":region_name => ").append(rbStringLiteral(regionName)).append(", ")
                .append(":country_code => ").append(rbStringLiteral(countryCode)).append(", ")
                .append(":country_name => ").append(rbStringLiteral(countryName)).append(", ")
                .append(":postal_code => ").append(rbStringLiteral(postalCode)).append(", ")
                .append(":metro_code => ").append(metroCode).append(", ")
                .append(":metro_name => ").append(rbStringLiteral(metroName)).append(", ")
                .append(":time_zone => ").append(rbStringLiteral(timeZone)).append(", ")
                .append(":area_code => ").append(areaCode).append(", ")
                .append(":longitude => ").append(longitude).append(", ")
                .append(":latitude => ").append(latitude)
                .append("}");
        return sb.toString();
    }

    public String toPhp() {
        StringBuilder sb = new StringBuilder()
                .append("array(")
                .append("\"name\" => ").append(phpStringLiteral(name)).append(", ")
                .append("\"region_code\" => ").append(phpStringLiteral(regionCode)).append(", ")
                .append("\"region_name\" => ").append(phpStringLiteral(regionName)).append(", ")
                .append("\"country_code\" => ").append(phpStringLiteral(countryCode)).append(", ")
                .append("\"country_name\" => ").append(phpStringLiteral(countryName)).append(", ")
                .append("\"postal_code\" => ").append(phpStringLiteral(postalCode)).append(", ")
                .append("\"metro_code\" => ").append(metroCode).append(", ")
                .append("\"metro_name\" => ").append(phpStringLiteral(metroName)).append(", ")
                .append("\"time_zone\" => ").append(phpStringLiteral(timeZone)).append(", ")
                .append("\"area_code\" => ").append(areaCode).append(", ")
                .append("\"longitude\" => ").append(longitude).append(", ")
                .append("\"latitude\" => ").append(latitude)
                .append(")");
        return sb.toString();
    }


}
