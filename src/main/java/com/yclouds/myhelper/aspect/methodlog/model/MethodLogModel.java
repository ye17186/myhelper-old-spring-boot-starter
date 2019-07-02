package com.yclouds.myhelper.aspect.methodlog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.yclouds.myhelper.aspect.methodlog.RetTypeEnum;
import com.yclouds.myhelper.utils.DateUtils;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 方法日志对象模型
 *
 * @author ye17186
 * @version 2019/6/28 15:23
 */
@Setter
@Getter
public class MethodLogModel implements Serializable {

    private static final long serialVersionUID = 5366214445464697640L;

    /**
     * 操作名
     */
    private String action;

    /**
     * 入参
     */
    private JsonNode params;

    /**
     * 返回结果
     */
    private Object result;

    /**
     * 返回类型（OK=正常；EX=异常）
     */
    private RetTypeEnum retType;

    /**
     * 进入时间
     */
    @JsonFormat(pattern = DateUtils.PATTERN_DATETIME_01)
    private LocalDateTime startTime;

    /**
     * 耗时，单位毫秒
     */
    private Long duration;
}
