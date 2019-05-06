package com.yclouds.myhelper.autoconfigure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ye17186
 * @version 2019/5/6 15:20
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "myhelper.plugins.advice")
@Component
public class AdviceProperties {

    /**
     * 是否激活Controller增强插件
     */
    private boolean enabled;
}
