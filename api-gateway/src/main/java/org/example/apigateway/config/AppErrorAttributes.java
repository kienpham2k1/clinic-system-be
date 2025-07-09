package org.example.apigateway.config;

import org.example.apigateway.exception.JwtAuthException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = getError(request);
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", determineHttpStatus(error));
        errorAttributes.put("path", request.path());
        errorAttributes.put("timestamp", System.currentTimeMillis());
        errorAttributes.put("error", error.getMessage());
        return errorAttributes;
    }

    private int determineHttpStatus(Throwable error) {
        if (error instanceof JwtAuthException) {
            return 401;
        }
        // Add more custom exception mappings if needed
        return 500;
    }
}