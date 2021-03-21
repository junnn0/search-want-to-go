package com.junyoung.searchwheretogoapi.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private ApiResponseHeader header;
    private T body;

    public static <T> ApiResponse<T> success(T body) {
        ApiResponseHeader header = new ApiResponseHeader(true, 0, "SUCCESS");
        return new ApiResponse<>(header, body);
    }

    public static <T> ApiResponse<T> fail(ResponseType responseType) {
        ApiResponseHeader header =
                new ApiResponseHeader(false, responseType.getCode(), responseType.getMessage());
        return new ApiResponse<>(header, null);
    }

    public static <T> ApiResponse<T> fail(int resultCode, String resultMessage) {
        ApiResponseHeader header = new ApiResponseHeader(false, resultCode, resultMessage);
        return new ApiResponse<>(header, null);
    }
}
