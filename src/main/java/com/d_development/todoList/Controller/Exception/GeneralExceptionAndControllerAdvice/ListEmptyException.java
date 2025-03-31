package com.d_development.todoList.Controller.Exception.GeneralExceptionAndControllerAdvice;

import org.springframework.http.HttpStatus;

public class ListEmptyException extends CentralException {
    public ListEmptyException(String text, String value) {
        super("There is no elements in the BD with respect to petition -> ["+text+"]",
                "L-600",
                "ListEmpty",
                value,
                HttpStatus.BAD_REQUEST);
    }
}
