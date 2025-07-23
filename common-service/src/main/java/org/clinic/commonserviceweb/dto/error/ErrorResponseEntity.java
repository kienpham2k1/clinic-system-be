package org.clinic.commonserviceweb.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ErrorResponseEntity {
    private int status;
    private String message;
    private String error;
    @JsonInclude(value = Include.NON_NULL)
    private Map<String, String> validationErrors;
    private String path;
    @lombok.Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
