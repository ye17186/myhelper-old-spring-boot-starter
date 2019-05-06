package com.yclouds.myhelper.plugins.token;

import com.yclouds.myhelper.autoconfigure.properties.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ye17186
 * @version 2019/5/6 13:15
 */
@Configuration
@EnableConfigurationProperties(TokenProperties.class)
@ConditionalOnProperty(prefix = "myhelper.plugins.token", name = "enabled", havingValue = "true")
public class TokenConfiguration implements WebMvcConfigurer {

    @Autowired
    TokenProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public TokenService tokenService() {
        return new TokenService(properties.getTimeout(), properties.getTokenPrefix());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TokenInterceptor tokenInterceptor = new TokenInterceptor();
        tokenInterceptor.setProperties(properties);
        tokenInterceptor.setTokenService(tokenService());

        InterceptorRegistration registration = registry.addInterceptor(tokenInterceptor);
        if (properties.getPathPatterns() != null) {
            registration.addPathPatterns(properties.getPathPatterns());
        }
        if (properties.getExcludePathPatterns() != null) {
            registration.excludePathPatterns(properties.getExcludePathPatterns());
        }
    }
}
