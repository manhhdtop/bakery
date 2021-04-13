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
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ApiBaseResponse error = ApiBaseResponse.error(getMessage("message.bad_request"), errors);
        return ResponseEntity.badRequest().body(error);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        throw new UnauthorizedException();
    }
}