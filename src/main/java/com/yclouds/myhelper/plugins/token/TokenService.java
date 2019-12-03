package com.yclouds.myhelper.plugins.token;

import com.yclouds.myhelper.utils.IdGenUtils;
import com.yclouds.myhelper.utils.RedisUtils;
import com.yclouds.myhelper.utils.StringUtils;
import com.yclouds.myhelper.web.error.code.BaseEnumError;
import com.yclouds.myhelper.web.error.code.IEnumError;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

/**
 * @author ye17186
 * @version 2019/5/5 15:22
 */
@Slf4j
@Setter
@Getter
public class TokenService {

    public static final String HEADER_TOKEN = "token";
    private static final String HEADER_TIMESTAMP = "timestamp";
    private static final String HEADER_NONCE = "nonce";
    private static final String HEADER_SIGN = "signature";

    private TokenProperties properties;

    public TokenService(TokenProperties properties) {
        this.properties = properties;
    }

    private String getKey(String token) {
        return properties.getTokenPrefix() + RedisUtils.KEY_SEPARATOR + token;
    }


    /**
     * 新建token，并存入Redis
     *
     * @param obj 带存对象
     * @return token
     */
    public <T> String create(T obj) {

        String token = IdGenUtils.uuid();
        RedisUtils.set(getKey(token), obj, properties.getTimeout());
        return token;
    }


    @SuppressWarnings("unchecked")
    public <T> T getUser(String token) {

        return (T) RedisUtils.get(getKey(token));
    }


    /**
     * 校验当前请求是否合
     *
     * @param request 请求
     * @return 非法信息，如果为null，则表示合法请求
     */
    @Nullable
    public IEnumError valid(HttpServletRequest request) {

        // Token
        String token = request.getHeader(HEADER_TOKEN);
        // 请发请求的时间戳
        String timestamp = request.getHeader(HEADER_TIMESTAMP);
        // 随机串
        String nonce = request.getHeader(HEADER_NONCE);
        // 签名
        String signature = request.getHeader(HEADER_SIGN);
        // 判断时间是否大于N秒，防止重放攻击
        long now = System.currentTimeMillis();

        if (StringUtils.isAnyEmpty(timestamp, nonce, signature) || !StringUtils
            .isNumeric(timestamp)) { // 除token外，所有参数都为必填项
            return BaseEnumError.SYSTEM_TOKEN_INVALID_01;
        } else if (!signature
            .equals(
                SignUtils.signature(token, timestamp, nonce, properties.getSecretKey()))) { // 校验签名
            return BaseEnumError.SYSTEM_TOKEN_INVALID_02;
        } else if (now - Long.valueOf(timestamp) > properties.getValidDuration() * 1000) { // 校验时间戳
            return BaseEnumError.SYSTEM_TOKEN_INVALID_03;
        } else if (!RedisUtils
            .setNx("NONCE:" + nonce, "NONCE:" + nonce,
                (long) properties.getValidDuration())) { // 校验随机串
            return BaseEnumError.SYSTEM_TOKEN_INVALID_04;
        }
        return null;
    }


}
