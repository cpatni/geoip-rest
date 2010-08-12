package ign.geoip.models;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;
import static org.apache.commons.lang.StringEscapeUtils.escapeJavaScript;
import static org.apache.commons.lang.StringEscapeUtils.escapeXml;


/**
 * User: cpatni
 * Date: Aug 7, 2010
 * Time: 11:06:20 AM
 */
public class Country {

    private final String code, name;

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String toXml() {
        return new StringBuilder().append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n")
                .append("<country>\n")
                .append("  <code>")
                .append(escapeXml(code))
                .append("</code>\n")
                .append("  <name>")
                .append(escapeXml(name))
                .append("</name>\n")
                .append("</country>").toString();
    }

    public String toJson() {
        return new StringBuilder().append("{\"code\": \"")
                .append(escapeJavaScript(code))
                .append("\", ")
                .append("\"name\": \"")
                .append(escapeJavaScript(name))
                .append("\"}").toString();
    }

    public String toJsonp(String callback) {
        return (callback == null ? "" : callback)+ "(" + toJson() + ")";

    }

    public String toRuby() {
        return new StringBuilder().append("{:code => \"")
                .append(escapeJavaScript(code))
                .append("\", ")
                .append(":name => \"")
                .append(escapeJava(name))
                .append("\"}").toString();
    }

    public String toPhp() {
        return new StringBuilder().append("array(\"code\" => \"")
                .append(escapeJava(code))
                .append("\", ")
                .append("\"name\" => \"")
                .append(escapeJava(name))
                .append("\")").toString();
    }

    public String toLegacyJson() {
        return new StringBuilder().append("( {\"class\":\"com.ign.geo.Country\", \"code\": \"")
                .append(escapeJavaScript(code))
                .append("\", ")
                .append("\"name\": \"")
                .append(escapeJava(name))
                .append("\"} )").toString();

    }
}
