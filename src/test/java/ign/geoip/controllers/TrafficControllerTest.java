package ign.geoip.controllers;

import com.google.inject.Provider;
import com.maxmind.geoip.LookupService;
import ign.geoip.models.Country;
import ign.geoip.models.GeoIPCity;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;

/**
 * User: cpatni
 * Date: Aug 8, 2010
 * Time: 11:31:14 PM
 */
public class TrafficControllerTest {
    @Test
    public void hcOnByDefault() {
        TrafficController tc = new TrafficController();
        assertTrue(tc.isOn());
    }

    @Test
    public void returnHealthy() throws IOException, ServletException {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("toggle")).andReturn(null).anyTimes();
        expect(request.getParameter("passcode")).andReturn(null).anyTimes();

        StringWriter output = new StringWriter();
        PrintWriter out = new PrintWriter(output);
        response.setContentType("text/html");
        expect(response.getWriter()).andReturn(out).anyTimes();
        replay(request, response);

        TrafficController tc = new TrafficController();
        tc.doGet(request, response);
        assertTrue(output.toString().indexOf("Healthy") >= 0);

    }

    @Test
    public void returnUnavailable() throws IOException, ServletException {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("toggle")).andReturn(null).anyTimes();
        expect(request.getParameter("passcode")).andReturn(null).anyTimes();

        StringWriter output = new StringWriter();
        PrintWriter out = new PrintWriter(output);
        response.setContentType("text/html");
        response.setStatus(503);

        expect(response.getWriter()).andReturn(out).anyTimes();
        replay(request, response);

        TrafficController tc = new TrafficController();
        tc.hc = false;
        assertFalse(tc.isOn());
        tc.doGet(request, response);
        assertTrue(output.toString().indexOf("Healthy") < 0);

    }

    @Test
    public void hcOff() throws IOException, ServletException {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("toggle")).andReturn("off").anyTimes();
        expect(request.getParameter("passcode")).andReturn("hluvsm!").anyTimes();
        expect(request.getHeader("X-Forwarded-For")).andReturn(null).anyTimes();
        expect(request.getRemoteAddr()).andReturn("10.2.3.4").anyTimes();

        StringWriter output = new StringWriter();
        PrintWriter out = new PrintWriter(output);
        response.setContentType("text/html");
        expect(response.getWriter()).andReturn(out).anyTimes();
        replay(request, response);

        TrafficController tc = new TrafficController();
        assertTrue(tc.isOn());
        tc.doGet(request, response);
        assertEquals("HC turned off by 10.2.3.4", output.toString().trim());
        assertFalse(tc.isOn());


    }

    @Test
    public void hcOn() throws IOException, ServletException {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("toggle")).andReturn("on").anyTimes();
        expect(request.getParameter("passcode")).andReturn("hluvsm!").anyTimes();
        expect(request.getHeader("X-Forwarded-For")).andReturn(null).anyTimes();
        expect(request.getRemoteAddr()).andReturn("10.2.3.4").anyTimes();

        StringWriter output = new StringWriter();
        PrintWriter out = new PrintWriter(output);
        response.setContentType("text/html");
        expect(response.getWriter()).andReturn(out).anyTimes();
        replay(request, response);

        TrafficController tc = new TrafficController();
        tc.hc = false;
        assertFalse(tc.isOn());
        tc.doGet(request, response);
        assertEquals("HC turned on by 10.2.3.4", output.toString().trim());
        assertTrue(tc.isOn());
    }

    @Test
    public void hcOnlyFromInternalHosts() throws IOException, ServletException {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);

        expect(request.getParameter("toggle")).andReturn("off").anyTimes();
        expect(request.getParameter("passcode")).andReturn("hluvsm!").anyTimes();
        expect(request.getHeader("X-Forwarded-For")).andReturn("192.16.0.3").anyTimes();
        expect(request.getRemoteAddr()).andReturn("10.2.3.4").anyTimes();

        StringWriter output = new StringWriter();
        PrintWriter out = new PrintWriter(output);
        response.setContentType("text/html");
        response.setStatus(412);
        expect(response.getWriter()).andReturn(out).anyTimes();
        replay(request, response);

        TrafficController tc = new TrafficController();
        assertTrue(tc.isOn());
        tc.doGet(request, response);

        assertTrue(tc.isOn());

    }


}
