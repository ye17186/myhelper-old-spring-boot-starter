package com.yclouds.myhelper.filter;

import com.yclouds.myhelper.constants.RequestConsts;
import com.yclouds.myhelper.utils.IdGenUtils;
import com.yclouds.myhelper.utils.RequestUtils;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * com.yclouds.myhelper.filter.ApiAccessFilter
 *
 * @author ye17186
 * @version 2019/7/2 13:18
 */
@Slf4j
@WebFilter(filterName = "ApiAccessFilter", urlPatterns = "/*")
public class ApiAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestId = IdGenUtils.nextIdStr();
        long start = System.currentTimeMillis();

        httpRequest.setAttribute(RequestConsts.REQUEST_ID, requestId);

        log.info("[MyHelper API] === start === ID: {}, URI: {}, Method: {}, ClientIP: {}",
            requestId, httpRequest.getRequestURL().toString(), httpRequest.getMethod(),
            RequestUtils.getIP(httpRequest));

        filterChain.doFilter(servletRequest, servletResponse);

        log.info("[MyHelper API] ===  end  === ID: {}, 耗时: {}ms", requestId,
            System.currentTimeMillis() - start);
    }
}
