package com.yclouds.myhelper.web.valid;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ye17186
 * @version 2019/6/11 14:04
 */
@Setter
@Getter
public class LogicMethodArgumentNotValidException extends RuntimeException {

    private static final long serialVersionUID = -615790386614615127L;
    private List<String> errors;

    LogicMethodArgumentNotValidException(List<String> errors) {
        super("Method Argument Not Valid Exception");
        this.errors = errors;
    }
}
