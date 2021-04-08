package com.bakery.server.exception;

import com.bakery.server.utils.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1616402340777827235L;

    public NotFoundException() {
        super(MessageUtils.getMessage("message.not_found"));
    }

    public NotFoundException(String message) {
        super(MessageUtils.getMessage(message));
    }

//    @Override
//    public synchronized Throwable fillInStackTrace() {
//        return this;
//    }
}
