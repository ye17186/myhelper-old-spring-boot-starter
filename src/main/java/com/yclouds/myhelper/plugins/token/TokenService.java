package com.yclouds.myhelper.plugins.token;

import com.yclouds.myhelper.utils.IdGenUtils;
import com.yclouds.myhelper.utils.RedisUtils;
import com.yclouds.myhelper.utils.StringUtils;
import com.yclouds.myhelper.web.error.code.BaseEnumError;
import com.yclouds.myhelper.web.error.code.IEnumError;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

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

    private static final String SERVER_HTTP_REQUEST_CLASS = "org.springframework.http.server.reactive.ServerHttpRequest";
    private static final String HTTP_SERVLET_REQUEST_CLASS = "javax.servlet.http.HttpServletRequest";

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
    public IEnumError valid(Object request) {

        TokenModel tokenModel = buildTokenModel(request);
        return doValid(tokenModel);
    }

    /**
     * 从Request中抽取参数
     *
     * @param request 请求退休
     * @return 校验对象
     */
    private TokenModel buildTokenModel(Object request) {

        String token = null; // Token
        String timestamp = null; // 请发请求的时间戳
        String nonce = null; // 随机串
        String signature = null; // 签名

        if (ClassUtils.isPresent(HTTP_SERVLET_REQUEST_CLASS, this.getClass().getClassLoader())
            && request instanceof HttpServletRequest) { // Zuul
            HttpServletRequest target = (HttpServletRequest) request;

            token = target.getHeader(HEADER_TOKEN);
            timestamp = target.getHeader(HEADER_TIMESTAMP);
            nonce = target.getHeader(HEADER_NONCE);
            signature = target.getHeader(HEADER_SIGN);

        } else if (ClassUtils.isPresent(SERVER_HTTP_REQUEST_CLASS, this.getClass().getClassLoader())
            && request instanceof ServerHttpRequest) { // Gateway

            ServerHttpRequest target = (ServerHttpRequest) request;

            List<String> tokens = target.getHeaders().get(HEADER_TOKEN);
            List<String> timestamps = target.getHeaders().get(HEADER_TIMESTAMP);
            List<String> nonces = target.getHeaders().get(HEADER_NONCE);
            List<String> signatures = target.getHeaders().get(HEADER_SIGN);
            if (!CollectionUtils.isEmpty(tokens)) {
                token = tokens.get(0);
            }
            if (!CollectionUtils.isEmpty(timestamps)) {
                timestamp = timestamps.get(0);
            }
            if (!CollectionUtils.isEmpty(nonces)) {
                nonce = nonces.get(0);
            }
            if (!CollectionUtils.isEmpty(signatures)) {
                signature = signatures.get(0);
            }
        }

        return new TokenModel(token, timestamp, nonce, signature);
    }

    /**
     * 执行校验
     *
     * @param model 校验对象
     * @return 校验结果
     */
    private IEnumError doValid(TokenModel model) {

        // 判断时间是否大于N秒，防止重放攻击
        long now = System.currentTimeMillis();

        if (StringUtils.isAnyEmpty(model.getTimestamp(), model.getNonce(), model.getSignature())
            || !StringUtils.isNumeric(model.getTimestamp())) { // 除token外，所有参数都为必填项
            return BaseEnumError.SYSTEM_TOKEN_INVALID_01;
        } else if (!model.getSignature().equals(SignUtils
            .signature(model.getToken(), model.getTimestamp(), model.getNonce(),
                properties.getSecretKey()))) { // 校验签名
            return BaseEnumError.SYSTEM_TOKEN_INVALID_02;
        } else if (now - Long.valueOf(model.getTimestamp())
            > properties.getValidDuration() * 1000) { // 校验时间戳
            return BaseEnumError.SYSTEM_TOKEN_INVALID_03;
        } else if (!RedisUtils.setNx("NONCE:" + model.getNonce(), "NONCE:" + model.getNonce(),
            (long) properties.getValidDuration())) { // 校验随机串
            return BaseEnumError.SYSTEM_TOKEN_INVALID_04;
        }
        return null;
    }


}
