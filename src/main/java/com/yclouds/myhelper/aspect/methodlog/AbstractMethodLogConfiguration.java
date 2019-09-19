package com.yclouds.myhelper.aspect.methodlog;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

/**
 * @author ye17186
 * @version 2019/7/1 10:00
 */
@Configuration
public abstract class AbstractMethodLogConfiguration {

    MethodLogConfigurer configurer = new MethodLogConfigurer() {
    };

    @Autowired(required = false)
    void setConfigurers(Collection<MethodLogConfigurer> configurers) {
        if (CollectionUtils.isEmpty(configurers)) {
            return;
        }
        if (configurers.size() > 1) {
            throw new IllegalStateException("Only one MethodLogConfigurer is allowed.");
        }

        MethodLogConfigurer config = configurers.iterator().next();
        if (config != null) {
            this.configurer = config;
        }
    }
}
