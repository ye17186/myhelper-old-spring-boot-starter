package com.yclouds.myhelper.utils;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author ye17186
 * @version 2018/7/2 9:28
 */
@SuppressWarnings("unused")
public class RequestUtils {

    private RequestUtils(){}

    /**
     * 获取当前请求的 Request对象
     *
     * @return Request对象
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;

        return sra == null ? null : sra.getRequest();
    }

    /**
     * 获取发起当前请求的客户端IP
     * @return 客户端IP地址
     */
    public static String getIP() {
        return getIP(getRequest());
    }

    /**
     * 获取IP地址
     *
     * @param request 请求
     * @return request发起客户端的IP地址
     */
    public static String getIP(HttpServletRequest request) {
        if (request == null) {
            return "0.0.0.0";
        }

        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");

        String UNKNOWN_IP = "unknown";
        if (StringUtils.isNotEmpty(XFor) && !UNKNOWN_IP.equalsIgnoreCase(XFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }

        XFor = Xip;
        if (StringUtils.isNotEmpty(XFor) && !UNKNOWN_IP.equalsIgnoreCase(XFor)) {
            return XFor;
        }

        if (StringUtils.isBlank(XFor) || UNKNOWN_IP.equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || UNKNOWN_IP.equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || UNKNOWN_IP.equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || UNKNOWN_IP.equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || UNKNOWN_IP.equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }

}
