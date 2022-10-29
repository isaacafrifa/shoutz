package com.iam.shoutz.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    /* customize generic 500 error handler */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllGenericExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
                request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(ResourceNotFound ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(),
                request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ErrorDetails> handleUserAlreadyExistsException(ResourceAlreadyExists ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.CONFLICT.value(), ex.getMessage(),
                request.getDescription(false) , LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                   WebRequest request) {
        String error = "%s is not of the preferred type".formatted(ex.getValue());
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), error,
                request.getDescription(false) , LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errorList = ex.getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .toList()
                .toString()
                //clean errorList
                .replace("[","")
                .replace("]","");
        ErrorDetails errorDetails= new ErrorDetails(HttpStatus.BAD_REQUEST.value(), errorList,
                request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


}