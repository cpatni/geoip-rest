package ign.geoip.models;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * metro db
 * User: cpatni
 * Date: Aug 7, 2010
 * Time: 3:23:00 PM
 */
@Singleton
public class Metros {


    private Map<Integer, String> mappings;

    @Inject
    public Metros(@Named("metros") String text) {
        mappings = buildMappings(text);
    }

    Map<Integer, String> buildMappings(String text) {
        String[] lines = text.split("\n");
        Map<Integer, String> mappings = new HashMap<Integer, String>(250);
        for (String line : lines) {
            String[] parts = parseLine(line);
            mappings.put(Integer.valueOf(parts[1]), parts[0]);
        }
        return mappings;
    }

    public Map<Integer, String> getMappings() {
        return mappings;
    }

    String[] parseLine(String line) {
        String[] parts = line.split("\t");
        parts[0] = parts[0].substring(0, parts[0].length() - 3);
        return parts;
    }

    public String find(Integer id) {
        return mappings.get(id);
    }

}
