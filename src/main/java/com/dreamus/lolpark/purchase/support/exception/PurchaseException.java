package com.dreamus.lolpark.purchase.support.exception;

import com.dreamus.lolpark.purchase.type.PurchaseReasonCode;
import lombok.Getter;

public class PurchaseException extends RuntimeException {

    @Getter
    private final PurchaseReasonCode code;

    public PurchaseException(PurchaseReasonCode code, String message) {
        super(message);
        this.code = code;
    }
}
