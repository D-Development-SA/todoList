package com.d_development.todoList.Controller.Exception.GeneralExceptionAndControllerAdvice;

import org.springframework.http.HttpStatus;

public class ErrorUnexpectedException extends CentralException {
    public ErrorUnexpectedException() {
        super("Detected anomaly",
                "AS-1500",
                "Anomaly",
                "Nonexistent",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
