package com.yclouds.myhelper.interceptor;

import com.google.common.base.Charsets;
import com.yclouds.myhelper.utils.JsonUtils;
import com.yclouds.myhelper.web.response.ApiResp;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author ye17186
 * @version 2018/7/2 8:58
 */
public class BaseInterceptor implements HandlerInterceptor {

    protected void write(HttpServletRequest request, HttpServletResponse response, ApiResp resp)
        throws IOException {

        // 拦截器返回false时
        String origin = request.getHeader(HttpHeaders.ORIGIN);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        response.setCharacterEncoding(Charsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JsonUtils.obj2Json(resp));
    }
}
