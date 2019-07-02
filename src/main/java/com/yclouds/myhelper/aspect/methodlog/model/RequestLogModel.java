package com.yclouds.myhelper.aspect.methodlog.model;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ye17186
 * @version 2019/6/28 16:23
 */
@Setter
@Getter
public class RequestLogModel extends MethodLogModel {

    private static final long serialVersionUID = -5810923401946981125L;

    /**
     * Http请求ID
     */
    private String requestId;

    /**
     * Http请求URL
     */
    private String requestUrl;

    /**
     * Http请求方法，GET、POST等
     */
    private String requestMethod;

    /**
     * Http请求客户端IP
     */
    private String clientIp;
}
