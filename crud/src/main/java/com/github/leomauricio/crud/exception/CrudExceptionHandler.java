package com.github.leomauricio.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CrudExceptionHandler extends ResponseEntityExceptionHandler {
    public final ResponseEntity<ExeceptionResponse> handlerBadRequestException(Exception ex, WebRequest request) {
        ExeceptionResponse execeptionResponse = new ExeceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<ExeceptionResponse>(execeptionResponse, HttpStatus.BAD_REQUEST);
    }
}
