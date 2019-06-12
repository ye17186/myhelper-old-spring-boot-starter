package com.yclouds.myhelper.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MyHelper插件基类
 *
 * @author ye17186
 * @version 2019/6/6 16:25
 */
public abstract class AbstractPlugin {

    private Logger log = LoggerFactory.getLogger(getClass());

    protected void printLog() {
        log.info("[MyHelper] the MyHelper plugin [{}] has been enabled.",
            getClass().getSimpleName());
    }

}
