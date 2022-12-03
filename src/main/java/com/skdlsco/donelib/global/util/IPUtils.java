package com.skdlsco.donelib.global.util;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null)
            ip = request.getRemoteHost();
        return ip;
    }
}
