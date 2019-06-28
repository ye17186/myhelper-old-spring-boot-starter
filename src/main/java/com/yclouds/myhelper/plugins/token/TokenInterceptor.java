package com.yclouds.myhelper.plugins.token;

import com.yclouds.myhelper.interceptor.BaseInterceptor;
import com.yclouds.myhelper.utils.RedisUtils;
import com.yclouds.myhelper.utils.StringUtils;
import com.yclouds.myhelper.web.error.code.BaseEnumError;
import com.yclouds.myhelper.web.response.ApiResp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.cors.CorsUtils;

/**
 * Token拦截器，基于token、timestamp、nonce、signature进行检验
 *
 * @author ye17186
 * @version 2019/5/6 13:55
 */
@Setter
@Getter
public class TokenInterceptor extends BaseInterceptor {

    private static final String HEADER_TOKEN = "token";
    private static final String HEADER_TIMESTAMP = "timestamp";
    private static final String HEADER_NONCE = "nonce";
    private static final String HEADER_SIGN = "signature";

    private TokenProperties properties;
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        // Token
        String token = request.getHeader(HEADER_TOKEN);
        // 请发请求的时间戳
        String timestamp = request.getHeader(HEADER_TIMESTAMP);
        // 随机串
        String nonce = request.getHeader(HEADER_NONCE);
        // 签名
        String sign = request.getHeader(HEADER_SIGN);

        if (StringUtils.isAnyEmpty(token, timestamp, nonce, sign) || !StringUtils
            .isNumeric(timestamp)) {
            write(request, response, ApiResp.retFail(BaseEnumError.SYSTEM_TOKEN_INVALID_01));
            return false;
        }

        // 判断时间是否大于N秒，防止重放攻击
        long now = System.currentTimeMillis();
        if (now - Long.valueOf(timestamp) > properties.getValidDuration() * 1000) {
            write(request, response, ApiResp.retFail(BaseEnumError.SYSTEM_TOKEN_INVALID_02));
            return false;
        }

        // 随机串存在，则说明可能是重放，此处禁止
        if (RedisUtils.get("NONCE:" + nonce) != null) {
            write(request, response, ApiResp.retFail(BaseEnumError.SYSTEM_TOKEN_INVALID_03));
            return false;
        }

        // 签名错误
        String signature = SignUtils.signature(token, timestamp, nonce, properties.getSecretKey());
        if (!sign.equals(signature)) {
            write(request, response, ApiResp.retFail(BaseEnumError.SYSTEM_TOKEN_INVALID_04));
            return false;
        }

        // 随机串存入Redis，nonce只能被使用一次
        RedisUtils.set("NONCE:" + nonce, properties.getValidDuration());
        return true;
    }
}
