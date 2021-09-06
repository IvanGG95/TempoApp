package com.TFG.tempo.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers, HttpStatus status,
                                                                WebRequest request) {

    List<String> errors = new ArrayList<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {

      String message = error.getDefaultMessage();
      errors.add(message);
    });
    return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
  }
}
