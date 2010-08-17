package ign.geoip.helpers;

import javax.servlet.http.HttpServletRequest;

/**
 * User: cpatni
 * Date: Aug 8, 2010
 * Time: 11:42:21 AM
 */
public class ApplicationHelper {
    private ApplicationHelper() {
    }

    public static <T> T tryNonNull(T first, T second) {
        return first != null ? first : second;
    }


    public static String getIP(String ip, HttpServletRequest request) {
        if("self".equals(ip) || "current".equals(ip) || isTrivial(ip)) {
            return getTrueClientIPFromXFF(request);
        } else {
            return ip;
        }

    }

    public static boolean isNonTrivial(String s) {
        return s != null && !"".equals(s.trim());
    }

    public static boolean isTrivial(String s) {
        return s == null || "".equals(s.trim());
    }

    public static String getTrueClientIPFromXFF(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (isNonTrivial(xff)) {
            xff = xff.trim();
            int index = xff.indexOf(",");
            if (index > 0) {
                return xff.substring(0, index).trim();
            } else {
                return xff;
            }

        }
        return request.getRemoteAddr();
    }


}
