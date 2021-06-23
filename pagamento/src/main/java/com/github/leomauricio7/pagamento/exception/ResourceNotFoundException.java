package com.github.leomauricio7.pagamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 3808768503760943407L;

    public ResourceNotFoundException(String exception) {
        super(exception);
    };
}

