package com.yclouds.myhelper.plugins.token;

import com.yclouds.myhelper.plugins.AbstractPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基于Token的接口安全插件
 *
 * @author ye17186
 * @version 2019/5/6 13:15
 */
@Configuration
@EnableConfigurationProperties(TokenProperties.class)
@ConditionalOnProperty(prefix = "myhelper.plugins.token", name = "enabled", havingValue = "true")
public class TokenPlugin extends AbstractPlugin {

    public TokenPlugin() {
        printLog();
    }

    @Autowired
    TokenProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public TokenService tokenService() {
        return new TokenService(properties);
    }
}
