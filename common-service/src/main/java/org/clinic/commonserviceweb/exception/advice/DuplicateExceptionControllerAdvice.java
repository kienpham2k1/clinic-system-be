package org.clinic.commonserviceweb.exception.advice;

import lombok.extern.slf4j.Slf4j;
import org.clinic.commonserviceweb.exception.DuplicateException;
import org.clinic.commonserviceweb.exception.dto.error.ErrorResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class DuplicateExceptionControllerAdvice {

    @ExceptionHandler({DuplicateException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public <T extends RuntimeException> ErrorResponseEntity resourceNotFoundExceptionHandler(T ex, WebRequest request) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.error("Exception: %s, description: %s, message: %s".formatted(ex.getClass().getSimpleName(), errorResponse.getPath(), ex.getMessage()));
        return errorResponse;
    }
}
