package com.dreamus.lolpark.purchase.support.exception;

import com.dreamus.lolpark.purchase.dto.ResultDto;
import com.dreamus.lolpark.purchase.type.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(ResourceNotFoundedException.class)
    public ResponseEntity<?> resourceNotFounded(ResourceNotFoundedException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ResultDto.of(ResponseCode.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(PurchaseException.class)
    public ResponseEntity<?> purchaseException(PurchaseException exception) {
        return ResponseEntity.badRequest().body(ResultDto.of(exception.getCode(), exception.getMessage()));
    }
}
