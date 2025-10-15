package org.clinic.common_service_web.wrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.clinic.common_service_web.wrapper.dto.BaseResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().equals(String.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        // ⛔️ Không wrap nếu là Swagger/OpenAPI
        String path = request.getURI().getPath();
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            return body;
        }

        if (response instanceof ServletServerHttpResponse servletResponse) {
            int status = servletResponse.getServletResponse().getStatus();

            // ❌ Nếu là lỗi (khác 2xx), không wrap lại
            if (status >= 400) {
                return body;
            }
        }

        // ✅ Nếu đã là BaseResponse rồi thì không cần wrap nữa
        if (body instanceof BaseResponse) {
            return body;
        }
        if (body instanceof String) {
            // Phải tự convert thủ công sang JSON vì Spring không thể wrap object và vẫn trả về text/plain
            try {
                ObjectMapper mapper = new ObjectMapper();
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return mapper.writeValueAsString(BaseResponse.success(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to wrap String body", e);
            }
        }
        return BaseResponse.success(body);
    }
}