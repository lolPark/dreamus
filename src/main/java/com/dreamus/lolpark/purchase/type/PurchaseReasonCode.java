package com.dreamus.lolpark.purchase.type;

import com.dreamus.lolpark.purchase.constants.MessageConstants;
import lombok.Getter;

public enum PurchaseReasonCode {
    NOT_MATCHED_PRICE(MessageConstants.NOT_MATCHED_PRICE),
    NO_USER_DATA(MessageConstants.NO_USER_DATA),
    NOT_VALID_PRODUCT(MessageConstants.NOT_VALID_PRODUCT);

    @Getter
    private final String message;

    PurchaseReasonCode(String message) {
        this.message = message;
    }
}
