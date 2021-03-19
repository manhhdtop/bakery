package com.bakery.server.exception;

import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static com.bakery.server.utils.MessageUtils.getMessage;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final void handleAllExceptions(Exception ex, WebRequest request) throws Exception {
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
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(getMessage(error.getDefaultMessage()));
        }
        ApiBaseResponse error = ApiBaseResponse.error(getMessage("message.badRequest"), details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}