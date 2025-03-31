package com.d_development.todoList.Controller.Exception.GeneralExceptionAndControllerAdvice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CentralException extends RuntimeException implements ICentralException {
    protected String code;
    protected String type;
    protected HttpStatus httpStatus;
    protected String valueField;

    public CentralException(String message, String code, String type, String valueField, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.type = type;
        this.valueField = valueField;
        this.httpStatus = httpStatus;
    }

}
