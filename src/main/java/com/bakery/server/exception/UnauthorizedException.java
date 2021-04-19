package com.bakery.server.exception;

import com.bakery.server.utils.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = 1616402540177823135L;

    public UnauthorizedException() {
        super(MessageUtils.getMessage("message.unauthorized"));
    }

    public static UnauthorizedException build() {
        return new UnauthorizedException();
    }

//    @Override
//    public synchronized Throwable fillInStackTrace() {
//        return this;
//    }
}
