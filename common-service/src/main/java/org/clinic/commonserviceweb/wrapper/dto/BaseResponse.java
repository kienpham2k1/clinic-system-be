package org.clinic.commonserviceweb.wrapper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    private String status;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> res = new BaseResponse<>();
        res.setStatus("SUCCESS");
        res.setMessage("OK");
        res.setData(data);
        return res;
    }

    public static <T> BaseResponse<T> error(String message, T data) {
        BaseResponse<T> res = new BaseResponse<>();
        res.setStatus("ERROR");
        res.setMessage(message);
        res.setData(data);
        return res;
    }
}