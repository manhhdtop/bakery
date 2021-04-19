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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.bakery.server.utils.MessageUtils.getMessage;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler implements AuthenticationFailureHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public final void handleAllExceptions(Exception ex, WebRequest request) {
        throw new RuntimeExceptionHandling(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public final void handleUnauthorizedException(AccessDeniedException ex, WebRequest request) {
        throw new AccessDeniedException();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public final void handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        throw UnauthorizedException.build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public final void handleNotFoundException(NotFoundException ex, WebRequest request) {
        throw ex;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public final void handleBadRequestException(BadRequestException ex, WebRequest request) {
        throw ex;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public final void handleSizeLimitExceededException(MaxUploadSizeExceededException ex, WebRequest request) {
        throw BadRequestException.build("file.limit_exceeded");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            details.add(error.getField() + ": " + error.getDefaultMessage());
        }
        ApiBaseResponse error = ApiBaseResponse.error(getMessage("message.bad_request"), details);
        return ResponseEntity.badRequest().body(error);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        throw new UnauthorizedException();
    }
}