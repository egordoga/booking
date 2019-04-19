package ua.booking.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ArgsWrongException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String dateWrongHandler(ArgsWrongException ex) {
        return ex.getMessage();
    }
}
