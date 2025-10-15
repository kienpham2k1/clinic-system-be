package org.clinic.common_service_web.exception.advice;

import lombok.extern.slf4j.Slf4j;
import org.clinic.common_service_web.exception.AccessDeniedException;
import org.clinic.common_service_web.exception.dto.error.ErrorResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class SecurityExceptionControllerAdvice {

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public <T extends RuntimeException> ErrorResponseEntity resourceAccessDeniedExceptionHandler(T ex, WebRequest request) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.error("Exception: %s, description: %s, message: %s".formatted(ex.getClass().getSimpleName(), errorResponse.getPath(), ex.getMessage()));
        return errorResponse;
    }
}
