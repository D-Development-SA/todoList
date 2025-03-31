package com.d_development.todoList.Controller.Exception.BDExcepcion;

import com.d_development.todoList.Controller.Exception.GeneralExceptionAndControllerAdvice.CentralException;
import org.springframework.http.HttpStatus;

public class AnomalyFoundBDException extends CentralException {
    public AnomalyFoundBDException(String valueField) {
        super("Anomaly detected in the BD",
                "BD-400",
                "AnomalyBD",
                valueField,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
