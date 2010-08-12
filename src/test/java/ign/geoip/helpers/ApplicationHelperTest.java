package ign.geoip.helpers;

import junit.framework.TestCase;
import junit.framework.Assert;
import org.junit.Test;

import static ign.geoip.helpers.ApplicationHelper.getIP;
import static ign.geoip.helpers.ApplicationHelper.isTrivial;
import static ign.geoip.helpers.ApplicationHelper.isNonTrivial;
import static org.easymock.EasyMock.*;

import javax.servlet.http.HttpServletRequest;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertFalse;


/**
 * User: cpatni
 * Date: Aug 8, 2010
 * Time: 1:45:46 PM
 */
public class ApplicationHelperTest {

    @Test
    public void tryNonNullTest() throws Exception {
        String s1 = "foo";
        String s2 = ApplicationHelper.tryNonNull(s1, "bar");
        assertSame(s1, s2);

        Long n1 = new Long(42);
        Long n2 = ApplicationHelper.tryNonNull(null, n1);
        assertSame(n1, n2);

        assertNull(ApplicationHelper.tryNonNull(null, null));

    }


    @Test
    public void specifiedIPTest() {
        assertEquals("212.101.97.206", getIP("212.101.97.206", null));
    }

    @Test
    public void xffTest() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader("X-Forwarded-For")).andReturn("45.43.4.5,212.101.97.206,127.0.0.1");
        replay(request);
        assertEquals("45.43.4.5", getIP(null, request));
    }


    @Test
    public void socketIpTestEmptyXFF() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader("X-Forwarded-For")).andReturn("  ");
        expect(request.getRemoteAddr()).andReturn("212.101.97.206");
        replay(request);
        assertEquals("212.101.97.206", getIP(null, request));
    }

    @Test
    public void socketIpTestNullXFF() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader("X-Forwarded-For")).andReturn(null);
        expect(request.getRemoteAddr()).andReturn("212.101.97.206");
        replay(request);
        assertEquals("212.101.97.206", getIP(null, request));
    }

    @Test
    public void testNullAsTrivial() {
        assertTrue(isTrivial(null));
    }

    @Test
    public void testBlankAsTrivial() {
        assertTrue(isTrivial("   "));
    }

    @Test
    public void testTrivialTueNegatives() {
        assertFalse(isTrivial("Hello"));
    }

    @Test
    public void testNullAsNonTrivialNegative() {
        assertFalse(isNonTrivial(null));
    }

    @Test
    public void testBlankAsNonTrivialNegative() {
        assertFalse(isNonTrivial("   "));
    }

    @Test
    public void testHelloAsTrivial() {
        assertTrue(isNonTrivial("Hello"));
    }




}
