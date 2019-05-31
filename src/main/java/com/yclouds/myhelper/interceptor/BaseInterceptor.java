package com.yclouds.myhelper.interceptor;

import com.yclouds.myhelper.utils.JsonUtils;
import com.yclouds.myhelper.web.response.ApiResp;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author ye17186
 * @version 2018/7/2 8:58
 */
public class BaseInterceptor implements HandlerInterceptor {

    protected void write(HttpServletRequest request, HttpServletResponse response, ApiResp resp)
        throws IOException {

        // 拦截器返回false时
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JsonUtils.obj2Json(resp));
    }
}
