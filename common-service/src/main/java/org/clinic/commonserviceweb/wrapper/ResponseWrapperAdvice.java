package org.clinic.commonserviceweb.wrapper;

import org.clinic.commonserviceweb.dto.BaseResponse;
import org.clinic.commonserviceweb.dto.ResponseStatus;
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
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
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

        return BaseResponse.success(body);
    }
}