package com.bakery.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RuntimeExceptionHandling extends RuntimeException {

    private static final long serialVersionUID = 1616402540757427935L;

    public RuntimeExceptionHandling(String message) {
        super(message);
    }

//    @Override
//    public synchronized Throwable fillInStackTrace() {
//        return this;
//    }
}
