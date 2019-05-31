package com.yclouds.myhelper.autoconfigure;

import com.yclouds.myhelper.context.SpringContextHandler;
import com.yclouds.myhelper.plugins.advice.ControllerAdviceConfiguration;
import com.yclouds.myhelper.plugins.token.TokenConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author ye17186
 * @version 2019/5/5 15:15
 */
@Configuration
@Import({SpringContextHandler.class,
    TokenConfiguration.class,
    ControllerAdviceConfiguration.class})
public class MyHelperAutoConfiguration {

}
