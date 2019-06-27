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
public class LogicArgNoValidException extends RuntimeException {

    private static final long serialVersionUID = -615790386614615127L;
    private List<String> errors;

    LogicArgNoValidException(List<String> errors) {
        super("Args No Valid.");
        this.errors = errors;
    }

    LogicArgNoValidException(List<String> errors, String message) {

        super(message);
        this.errors = errors;
    }
}
