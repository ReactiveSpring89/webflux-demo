package com.app.demo.exception.exceptionhandler;

import com.app.demo.exception.InputValidationException;
import com.app.demo.models.dto.InputValidationFailedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebfluxDemoExceptionHandler {
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputValidationFailedResponse> handleException(InputValidationException ex) {
        InputValidationFailedResponse response = new InputValidationFailedResponse();
        response.setErrorCode(ex.getErrorCode());
        response.setInput(ex.getInput());
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
