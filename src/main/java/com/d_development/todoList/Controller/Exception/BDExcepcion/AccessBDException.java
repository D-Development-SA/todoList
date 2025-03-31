package com.d_development.todoList.Controller.Exception.BDExcepcion;

import com.d_development.todoList.Controller.Exception.GeneralExceptionAndControllerAdvice.CentralException;
import org.springframework.http.HttpStatus;

public class AccessBDException extends CentralException {
    public AccessBDException() {
        super("Cannot were perform the query to the BD correctly",
                "BD-301",
                "ErrorQuery",
                "???",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
