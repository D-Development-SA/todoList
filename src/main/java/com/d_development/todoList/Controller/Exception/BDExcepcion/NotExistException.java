package com.d_development.todoList.Controller.Exception.BDExcepcion;

import com.d_development.todoList.Controller.Exception.GeneralExceptionAndControllerAdvice.CentralException;
import org.springframework.http.HttpStatus;

public class NotExistException extends CentralException {
    public NotExistException(Long id, String value) {
        super("There is no element with the id specify in the BD -> ["+id+"]",
                "BD-300",
                "NotExistInBD",
                value,
                HttpStatus.NOT_FOUND);
    }
    public NotExistException(String text, String value) {
        super("There is no element with the field specify in the BD -> ["+text+"]",
                "BD-300",
                "NotExistInBD",
                value,
                HttpStatus.NOT_FOUND);
    }
}
