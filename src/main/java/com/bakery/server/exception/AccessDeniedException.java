package com.bakery.server.exception;

import com.bakery.server.utils.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException {

    private static final long serialVersionUID = 1616402540177823135L;

    public AccessDeniedException() {
        super(MessageUtils.getMessage("message.access_denied"));
    }

//    @Override
//    public synchronized Throwable fillInStackTrace() {
//        return this;
//    }
}
