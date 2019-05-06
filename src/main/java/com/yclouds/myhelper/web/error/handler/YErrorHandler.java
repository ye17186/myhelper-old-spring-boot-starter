package com.yclouds.myhelper.web.error.handler;

import com.yclouds.myhelper.web.annotation.YRestController;
import com.yclouds.myhelper.web.error.code.BaseError;
import com.yclouds.myhelper.web.error.code.HttpError;
import com.yclouds.myhelper.web.response.ApiResp;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * 自定义错误处理
 *
 * @author ye17186
 * @version 2019/3/21 16:05
 */
@Slf4j
@YRestController
public class YErrorHandler implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public ApiResp<HttpError> error(HttpServletRequest request,
        HttpServletResponse response) {

        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> error = errorAttributes.getErrorAttributes(webRequest, false);

        response.setStatus(HttpStatus.OK.value());
        int status = (int) error.get("status");
        String path = (String) error.get("path");
        String msg = (String) error.get("error");

        log.warn("[System Exception] path: {}, error: {}, status: {}", path, msg, status);

        return ApiResp
            .retFail(BaseError.SYSTEM_EXCEPTION.getCode(), BaseError.SYSTEM_EXCEPTION.getMsg(),
                new HttpError(status, path, msg));
    }
}
