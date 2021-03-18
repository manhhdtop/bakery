package com.bakery.server.exception;

import com.bakery.server.utils.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1616402540777823135L;

    public UnAuthorizedException() {
        super(MessageUtils.getMessage("message.unauthorized"));
    }
}
