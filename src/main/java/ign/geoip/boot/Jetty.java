package ign.geoip.boot;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;


/**
 * User: cpatni
 * Date: Aug 7, 2010
 * Time: 6:54:32 PM
 */
@SuppressWarnings("")
public class Jetty {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar("src/main/webapp");
        server.setHandler(webapp);

        server.start();
        server.join();

        
    }
}
