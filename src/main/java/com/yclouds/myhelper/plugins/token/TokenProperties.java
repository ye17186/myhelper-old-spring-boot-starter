package com.yclouds.myhelper.plugins.token;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ye17186
 * @version 2019/5/5 15:16
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "myhelper.plugins.token")
@Component
public class TokenProperties {

    /**
     * 是否激活Token插件
     */
    private boolean enabled;

    /**
     * 拦截url
     */
    private String[] pathPatterns;

    /**
     * 排除url
     */
    private String[] excludePathPatterns;

    /**
     * 基于Redis的token有效期
     */
    private long timeout;

    /**
     * 基于Redis的token key前缀
     */
    private String tokenPrefix;

    /**
     * 签名算法的密钥
     */
    private String secretKey;

    /**
     * 时间戳有效时间间隔，默认30s
     */
    private int validDuration = 30;

}
