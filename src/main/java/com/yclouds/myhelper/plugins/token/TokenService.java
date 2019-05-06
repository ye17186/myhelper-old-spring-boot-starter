package com.yclouds.myhelper.plugins.token;

import com.yclouds.myhelper.utils.IdGenUtils;
import com.yclouds.myhelper.utils.RedisUtils;
import com.yclouds.myhelper.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ye17186
 * @version 2019/5/5 15:22
 */
@Slf4j
@Setter
@Getter
@SuppressWarnings("unused")
public class TokenService {

    private static final long defaultTimeout = 7 * 24 * 3600;
    private static final String defaultTokenPrefix = "TOKEN";

    private Long timeout;
    private String tokenPrefix;

    public TokenService(long timeout, String tokenPrefix) {
        this.timeout = timeout == 0 ? defaultTimeout : timeout;
        this.tokenPrefix = StringUtils.isEmpty(tokenPrefix) ? defaultTokenPrefix : tokenPrefix;
        log.info("Plugin[TokenService]依赖于Redis, 请确保Redis已配置正确.");
    }

    private String getKey(String token) {
        return tokenPrefix + ":" + token;
    }


    /**
     * 新建token，并存入Redis
     *
     * @param obj 带存对象
     * @return token
     */
    public String create(Object obj) {

        String token = IdGenUtils.uuid();
        RedisUtils.set(getKey(token), obj, timeout);
        return token;
    }

    public Object getUser(String token) {

        return RedisUtils.get(getKey(token));
    }

}
