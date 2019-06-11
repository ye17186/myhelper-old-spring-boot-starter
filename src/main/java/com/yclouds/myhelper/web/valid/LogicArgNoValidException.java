package com.yclouds.myhelper.web.valid;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yemeng-lhq
 * @version 2019/6/11 14:04
 */
@Setter
@Getter
public class LogicArgNoValidException extends RuntimeException {

    private static final long serialVersionUID = -615790386614615127L;
    private List<String> errors;

    LogicArgNoValidException(List<String> errors) {
        this.errors = errors;
    }
}
