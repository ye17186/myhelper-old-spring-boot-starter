package com.yclouds.myhelper.exception;

import com.yclouds.myhelper.web.error.code.BaseError;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务逻辑异常
 *
 * @author ye17186
 * @version 2019/5/29 9:33
 */
@SuppressWarnings("unused")
@Setter
@Getter
public class LogicException extends RuntimeException {

    private static final long serialVersionUID = -1026132894444019376L;

    /**
     * 异常码
     */
    private int code;

    /**
     * 异常信息
     */
    private String msg;

    /**
     * 详细异常信息
     */
    private Object detail;

    public LogicException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public LogicException(int code, String msg, Object detail) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }

    public LogicException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public LogicException(int code, String msg, Object detail, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }

    public LogicException(BaseError error) {
        super(error.getMsg());
        this.code = error.getCode();
        this.msg = error.getMsg();
    }

    public LogicException(BaseError error, Throwable cause) {
        super(error.getMsg(), cause);
        this.code = error.getCode();
        this.msg = error.getMsg();
    }
}
