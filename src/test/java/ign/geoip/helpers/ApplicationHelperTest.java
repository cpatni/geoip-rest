package ign.geoip.helpers;

import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static ign.geoip.helpers.ApplicationHelper.getIP;
import static ign.geoip.helpers.ApplicationHelper.getTrueClientIPFromXFF;
import static ign.geoip.helpers.ApplicationHelper.isNonTrivial;
import static ign.geoip.helpers.ApplicationHelper.isTrivial;
import static ign.geoip.helpers.ApplicationHelper.tryNonNull;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


/**
 * User: cpatni
 * Date: Aug 8, 2010
 * Time: 1:45:46 PM
 */
public class ApplicationHelperTest {

    @Test
    public void privateConstructorTest() throws InstantiationException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Constructor<ApplicationHelper> cons = ApplicationHelper.class.getDeclaredConstructor();
        assertFalse(cons.isAccessible());
        cons.setAccessible(true);
        assertEquals(ApplicationHelper.class, cons.newInstance().getClass());

    }

    @Test
    public void tryNonNullFirstItem() throws Exception {
        String s1 = "foo";
        String s2 = tryNonNull(s1, "bar");
        assertEquals(s1, s2);

    }

    @Test
    public void testNonNullSecondItem() {
        Integer n1 = 42;
        Integer n2 = tryNonNull(null, n1);
        assertEquals(n1, n2);
    }

    @Test
    public void testNonNullAllNull() {
        assertNull(tryNonNull(null, null));
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
    public void selfAsIp() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader("X-Forwarded-For")).andReturn("  ");
        expect(request.getRemoteAddr()).andReturn("212.101.97.206");
        replay(request);
        assertEquals("212.101.97.206", getIP("self", request));
    }

    @Test
    public void currentAsIp() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader("X-Forwarded-For")).andReturn("  ");
        expect(request.getRemoteAddr()).andReturn("212.101.97.206");
        replay(request);
        assertEquals("212.101.97.206", getIP("current", request));
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
    public void getTrueClientIPFromXFFTest() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader("X-Forwarded-For")).andReturn("212.101.97.206");
        replay(request);
        assertEquals("212.101.97.206", getTrueClientIPFromXFF(request));

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
