package com.yclouds.myhelper.context;

import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * SpringContext处理类，用于将上下文注册到SpringUtils中
 *
 * @author ye17186
 * @version 2019/4/30 11:30
 */
@Slf4j
public class SpringContextHandler implements ApplicationContextAware {

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext)
        throws BeansException {
        SpringUtils.initContext(applicationContext);
        log.info("Spring Context自动初始化完成，可以通过SpringUtils类提供的方法来获取Bean.");
    }

}
