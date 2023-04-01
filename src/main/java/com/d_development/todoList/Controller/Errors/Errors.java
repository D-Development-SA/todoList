package com.d_development.todoList.Controller.Errors;

import lombok.Builder;
import lombok.Data;
import java.util.HashMap;

@Data
@Builder
public class Errors {
    private String code;
    private String tipo;
    private String message;
    private String valor;
    private HashMap<String, String> errores;
}
