package com.yclouds.myhelper.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yemeng-lhq
 * @version 2019/6/6 16:25
 */
public abstract class AbstractPlugin {

    private Logger log = LoggerFactory.getLogger(getClass());

    protected void printLog() {
        log.info("[MyHelper] the MyHelper plugin [{}] has been enabled.", getClass().getSimpleName());
    }

}
