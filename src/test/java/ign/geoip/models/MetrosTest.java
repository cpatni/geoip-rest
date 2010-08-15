package ign.geoip.models;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * User: cpatni
 * Date: Aug 12, 2010
 * Time: 11:58:52 PM
 */
public class MetrosTest {


    static String text = "Sacramento-Stockton-Modesto CA\t862\n" +
            "San Diego CA\t825\n" +
            "San Francisco-Oakland-San Jose CA\t807\n" +
            "Santa Barbara-Santa Maria-San Luis Obispo CA\t855\n" +
            "Yuma AZ-El Centro CA\t771";
    public static Metros subject() {
        return new Metros(text);
    }

    @Test
    public void findBayArea() {
        String bayarea = subject().find(807);
        assertEquals("San Francisco-Oakland-San Jose", bayarea);
    }

    @Test
    public void buildMappingsTest() {
        Map<Integer, String> mappings = subject().getMappings();
        assertEquals(5, mappings.size());
    }

    @Test
    public void parseLineTest() {
        String line = "Sacramento-Stockton-Modesto CA\t862";
        String[] parts = subject().parseLine(line);
        assertEquals("Sacramento-Stockton-Modesto", parts[0]);
        assertEquals("862", parts[1]);

    }


}
