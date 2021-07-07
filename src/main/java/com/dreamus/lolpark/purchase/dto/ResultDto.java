package com.dreamus.lolpark.purchase.dto;

import com.dreamus.lolpark.purchase.type.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto<T> {

    private T code;
    private String message;

    public static ResultDto<?> ok(String message) {
        return new ResultDto<>(ResponseCode.OK, message);
    }

    public static <T> ResultDto<?> of(T code, String message) {
        return new ResultDto<>(code, message);
    }
}
