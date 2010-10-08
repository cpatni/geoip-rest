package ign.geoip.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: cpatni
 * Date: Aug 14, 2010
 * Time: 12:05:30 PM
 */
public class GeoIPApiIntegrationTest {
    private static Server server;
    private static int port;

    private static String firstExists(String... files) {
        for (String file : files) {
            if (new File(file).exists()) {
                return file;
            }
        }
        return null;
    }

    @BeforeClass
    public static void init() throws Exception {

        System.setProperty("database", guessGeoIPCityDatabase());
        // Port 0 means "assign arbitrarily port number"
        server = new Server(0);

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar("src/main/webapp");
        server.setHandler(webapp);
        server.start();
        port = server.getConnectors()[0].getLocalPort();

    }

    private static String hr() {
        return "================================================================\n";
    }

    private static String spacer() {
        return "----------------------------------------------------------------\n";
    }

    private static String guessGeoIPCityDatabase() throws IOException {
        String[] locations = {"db/GeoIPCity.dat", "db/GeoLiteCity.dat", "/usr/local/share/GeoIPCity.dat"};

        String file = firstExists(locations);

        if (file == null) {
            String geoLiteDB = "db/GeoLiteCity.dat";
            String message = hr()+
                    "GeoIPCity database not found at " + Arrays.toString(locations) + ".\n" +
                    "Downloading GeoLiteCity.dat.gz and uncompromising to " + new File(geoLiteDB).getAbsolutePath() + " ....\n" +
                    "Should it fail, run following commands manually.\n" +
                    spacer()+
                    "cd " + System.getProperty("user.dir") + "\n" +
                    "mkdir -p db\n" +
                    "cd db\n" +
                    "wget http://geolite.maxmind.com/download/geoip/database/GeoLiteCity.dat.gz\n" +
                    "gunzip  GeoLiteCity.dat.gz\n"+
                    hr();
            System.out.println(message);
            wget("http://geolite.maxmind.com/download/geoip/database/GeoLiteCity.dat.gz", geoLiteDB);
            return geoLiteDB;
        }
        return file;

    }


    static class Response {
        int status;
        String message;
        Map<String, List<String>> headers;
        String contentType;
        String body;

        Response(int status, String message, String contentType, Map<String, List<String>> headers, String body) {
            this.status = status;
            this.message = message;
            this.contentType = contentType;
            this.headers = headers;
            this.body = body;
        }

        @Override
        public String toString() {
            return status + message + "\r\n" + body;
        }
    }

    private Response get(String uri) throws IOException {
        URL url = new URL("http", "localhost", port, uri);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        InputStream in = null;
        try {
            in = request.getInputStream();
        } catch (IOException e) {
            in = request.getErrorStream();
        }
        return new Response(request.getResponseCode(), request.getResponseMessage(), request.getContentType(), request.getHeaderFields(), asString(in));
    }

    public String asString(InputStream in) throws IOException {
        try {
            StringBuilder fileData = new StringBuilder(1000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            char[] buf = new char[1024];
            int numRead;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
            return fileData.toString();

        }
        finally {
            in.close();
        }
    }

    @Test
    public void testCountryXml() throws IOException {
        Response response = get("/countries/212.101.97.206.xml");
        assertEquals(200, response.status);
        assertEquals("OK", response.message);
        assertEquals("application/xml", response.contentType);
        assertTrue("X-Server is present", response.headers.get("X-Server").size() > 0);
        assertTrue("Contains </country> end tag", response.body.contains("</country>"));
    }

    @Test
    public void testCountryJson() throws IOException {
        Response response = get("/countries/212.101.97.206.json");
        assertEquals(200, response.status);
        assertEquals("OK", response.message);
        assertEquals("application/json", response.contentType);
        assertTrue("X-Server is present", response.headers.get("X-Server").size() > 0);
        assertTrue("Contains \"name\": ", response.body.contains("\"name\":"));
    }

    @Test
    public void testCountryPhp() throws IOException {
        Response response = get("/countries/212.101.97.206.php");
        assertEquals(200, response.status);
        assertEquals("OK", response.message);
        assertEquals("text/php", response.contentType);
        assertTrue("X-Server is present", response.headers.get("X-Server").size() > 0);
        assertTrue("Contains \"name\" => ", response.body.contains("\"name\" =>"));
        assertTrue("Contains array(", response.body.contains("array("));
    }

    @Test
    public void testCountryRuby() throws IOException {
        Response response = get("/countries/212.101.97.206.rb");
        assertEquals(200, response.status);
        assertEquals("OK", response.message);
        assertEquals("text/ruby", response.contentType);
        assertTrue("X-Server is present", response.headers.get("X-Server").size() > 0);
        assertTrue("Contains :name ", response.body.contains(":name"));
    }


    /////city test

    @Test
    public void testCityXml() throws IOException {
        Response response = get("/cities/212.101.97.206.xml");
        assertEquals(200, response.status);
        assertEquals("OK", response.message);
        assertEquals("application/xml", response.contentType);
        assertTrue("X-Server is present", response.headers.get("X-Server").size() > 0);
        assertTrue("Contains </city> end tag", response.body.contains("</city>"));
    }

    @Test
    public void testCityJson() throws IOException {
        Response response = get("/cities/212.101.97.206.json");
        assertEquals(200, response.status);
        assertEquals("OK", response.message);
        assertEquals("application/json", response.contentType);
        assertTrue("X-Server is present", response.headers.get("X-Server").size() > 0);
        assertTrue("Contains \"name\": ", response.body.contains("\"name\":"));
    }

    @Test
    public void testCityPhp() throws IOException {
        Response response = get("/cities/212.101.97.206.php");
        assertEquals(200, response.status);
        assertEquals("OK", response.message);
        assertEquals("text/php", response.contentType);
        assertTrue("X-Server is present", response.headers.get("X-Server").size() > 0);
        assertTrue("Contains \"name\" => ", response.body.contains("\"name\" =>"));
        assertTrue("Contains array(", response.body.contains("array("));
    }

    @Test
    public void testCityRuby() throws IOException {
        Response response = get("/cities/212.101.97.206.rb");
        assertEquals(200, response.status);
        assertEquals("OK", response.message);
        assertEquals("text/ruby", response.contentType);
        assertTrue("X-Server is present", response.headers.get("X-Server").size() > 0);
        assertTrue("Contains :name ", response.body.contains(":name"));
    }


    @Test
    public void testLocalhostJson() throws IOException {
        Response response = get("/cities/127.0.0.1.json");
        assertEquals(404, response.status);
        assertEquals("Not Found", response.message);
        assertEquals("text/plain", response.contentType);
        assertTrue("X-Server is present", response.headers.get("X-Server").size() > 0);
        assertEquals("There is no place like 127.0.0.1", response.body);
    }


    @AfterClass
    public static void shutdown() throws Exception {
        if (server != null) {
            server.stop();
        }
    }

    static void wget(String url, String filename) throws IOException {
        GZIPInputStream in = null;
        OutputStream out = null;
        try {
            // Open the compressed file
            //String inFilename = "GeoLiteCity.dat.gz";
            URL u = new URL(url);
            in = new GZIPInputStream(u.openStream());

            // Open the output file
            File file = new File(filename);
            file.getParentFile().mkdirs();
            out = new FileOutputStream(file);

            // Transfer bytes from the compressed file to the output file
            byte[] buf = new byte[1024 * 1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            // Close the file and stream
            in.close();
            out.close();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
