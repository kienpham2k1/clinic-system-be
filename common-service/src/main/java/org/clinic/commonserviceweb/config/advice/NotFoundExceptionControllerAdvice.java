package org.clinic.commonserviceweb.config.advice;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.clinic.commonserviceweb.dto.error.ErrorResponseEntity;
import org.clinic.commonserviceweb.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class NotFoundExceptionControllerAdvice {

    @ExceptionHandler({NotFoundException.class, EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public <T extends RuntimeException> ErrorResponseEntity resourceNotFoundExceptionHandler(T ex, WebRequest request) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.error("Exception: %s, description: %s, message: %s".formatted(ex.getClass().getSimpleName(), errorResponse.getPath(), ex.getMessage()));
        return errorResponse;
    }
}
