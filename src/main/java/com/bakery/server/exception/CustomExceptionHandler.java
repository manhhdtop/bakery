package com.bakery.server.exception;

import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.bakery.server.utils.MessageUtils.getMessage;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler implements AuthenticationFailureHandler {
    @ExceptionHandler(Exception.class)
    public final void handleAllExceptions(Exception ex, WebRequest request) {
        throw new RuntimeExceptionHandling(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final void handleUnauthorizedException(AccessDeniedException ex, WebRequest request) {
        throw ex;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final void handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        throw ex;
    }

    @ExceptionHandler(NotFoundException.class)
    public final void handleNotFoundException(NotFoundException ex, WebRequest request) {
        throw ex;
    }

    @ExceptionHandler(BadRequestException.class)
    public final void handleBadRequestException(BadRequestException ex, WebRequest request) {
        throw ex;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            details.add(error.getDefaultMessage());
        }
        ApiBaseResponse error = ApiBaseResponse.error(getMessage("message.badRequest"), details);
        return ResponseEntity.badRequest().body(error);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        throw new UnauthorizedException();
    }
}