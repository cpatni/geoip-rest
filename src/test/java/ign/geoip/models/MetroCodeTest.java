package ign.geoip.models;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: cpatni
 * Date: Aug 8, 2010
 * Time: 10:09:53 PM
 */
public class MetroCodeTest {

    @Test
    public void bayArea() {
        assertEquals("San Francisco-Oakland-San Jose", MetroCode.all().get(807));
    }

}
