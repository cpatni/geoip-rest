package ign.geoip.controllers;

import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: cpatni
 * Date: Aug 8, 2010
 * Time: 3:32:59 PM
 */
@Singleton
public class TrafficController extends HttpServlet {

    boolean hc = true;

    public boolean isOn() {
        return hc;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String toggle = request.getParameter("toggle");
        String passcode = request.getParameter("passcode");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        if(("on".equals(toggle) || "off".equals(toggle))) {
            if("hluvsm!".equals(passcode) && request.getHeader("X-Forwarded-For") == null) {
                hc = "on".equals(toggle);
                out.println("HC turned "+toggle +" by "+request.getRemoteAddr());
            } else{
                response.setStatus(412);
            }

        } else {
            if(hc) {
                out.println("<HTML><BODY>Healthy</BODY></HTML>");
            } else {
                response.setStatus(503);
                out.println("Service Unavailable");
            }
        }
    }
}
