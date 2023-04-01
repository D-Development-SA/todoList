package com.d_development.todoList.Controller.Exception.GeneralExceptionAndControllerAdvice;

import com.d_development.todoList.Controller.Errors.Errors;
import com.d_development.todoList.Controller.Exception.BDExcepcion.AccessBDException;
import com.d_development.todoList.Controller.Exception.BDExcepcion.NotExistException;
import com.d_development.todoList.Controller.Exception.UserException.ArgumentInvalidException;
import com.d_development.todoList.Controller.Exception.UserException.IncorrectFieldException;
import com.d_development.todoList.Controller.Exception.UserException.EmptyFieldException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Objects;

@RestControllerAdvice
public class ControllerAdvice {
    private HashMap<String, String> hashMap;
    @ExceptionHandler(value = {
            ListEmptyException.class,
            NotExistException.class,
            IncorrectFieldException.class,
            EmptyFieldException.class
    })
    public ResponseEntity<Errors> handlerFieldInc(ICentralException ex){
        Errors e = getBuild(ex, null);

        return new ResponseEntity<>(e, ex.getHttpStatus());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Errors> handlerValidateMethod(MethodArgumentNotValidException ex){
        IncorrectFieldException incorrectFieldException;
        hashMap = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> hashMap.put(((FieldError)error).getField(), error.getDefaultMessage()));

        incorrectFieldException = new IncorrectFieldException(hashMap.size()+" incorrect fields", "???");

        Errors e = getBuild(incorrectFieldException, hashMap);

        return new ResponseEntity<>(e, incorrectFieldException.getHttpStatus());
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Errors> argumentsInvalid (MethodArgumentTypeMismatchException ex){
        ArgumentInvalidException argumentInvalidException = new ArgumentInvalidException(ex.getParameter().getParameterName(), Objects.requireNonNull(ex.getValue()).toString());
        hashMap = new HashMap<>();

        hashMap.put("Info", ex.getLocalizedMessage().split(";")[0]);
        hashMap.put("Field", ex.getName());
        hashMap.put("Cause", ex.getCause().toString().split(":")[0]);
        hashMap.put("Info1", ex.getParameter().toString());
        hashMap.put("Value", Objects.requireNonNull(ex.getValue()).toString());

        Errors e = getBuild(argumentInvalidException, hashMap);

        return new ResponseEntity<>(e, argumentInvalidException.getHttpStatus());
    }

    @ExceptionHandler(value = DataAccessException.class)
    public ResponseEntity<Errors> noAccessBD(DataAccessException ex){
        hashMap = new HashMap<>();
        AccessBDException accessBDException = new AccessBDException();

        hashMap.put("Info", "Cannot were perform the query to the BD correctly");
        hashMap.put("Error", ex.getLocalizedMessage().split(";")[0]);
        hashMap.put("Cause", ex.getCause().toString().split(":")[0]);
        hashMap.put("Info1", ex.getMessage());

        Errors e = getBuild(accessBDException, hashMap);

        return new ResponseEntity<>(e, accessBDException.getHttpStatus());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Errors> handlerUnexpected(Exception ex){
        ErrorUnexpectedException exception = new ErrorUnexpectedException();
        hashMap = new HashMap<>();

        hashMap.put("Error", "Unexpected Error");
        hashMap.put("Message", ex.getMessage());
        hashMap.put("Specific message", ex.getLocalizedMessage());
        hashMap.put("Cause", ex.getCause().getLocalizedMessage());

        Errors e = getBuild(exception, hashMap);

        return new ResponseEntity<>(e, exception.getHttpStatus());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<HashMap<String, String>> handlerRuntime(RuntimeException ex){
        System.out.println(ex.getLocalizedMessage());
        System.out.println(ex.getMessage());
        System.out.println(ex.getCause().getMessage());

        hashMap = new HashMap<>();

        hashMap.put("Error", "Unexpected thread");
        hashMap.put("Message", ex.getMessage());
        hashMap.put("Specific message", ex.getLocalizedMessage());
        hashMap.put("Cause", ex.getCause().getLocalizedMessage());

        return new ResponseEntity<>(hashMap, HttpStatus.CONFLICT);
    }

    private Errors getBuild(ICentralException centralException, HashMap<String, String> hashMap) {
        return Errors.builder()
                .code(centralException.getCode())
                .tipo(centralException.getType())
                .message(centralException.getMessage())
                .valor(centralException.getValueField())
                .errores(hashMap)
                .build();
    }
}
