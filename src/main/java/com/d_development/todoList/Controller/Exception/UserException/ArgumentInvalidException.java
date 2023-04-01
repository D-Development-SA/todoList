package com.d_development.todoList.Controller.Exception.UserException;

import com.d_development.todoList.Controller.Exception.GeneralExceptionAndControllerAdvice.CentralException;
import org.springframework.http.HttpStatus;

public class ArgumentInvalidException extends CentralException {
    public ArgumentInvalidException(String text, String value) {
        super("Existence of one or many arguments invalid -> ["+text+"]",
                "C-102",
                "InvalidArgument",
                value,
                HttpStatus.BAD_REQUEST);
    }
}
