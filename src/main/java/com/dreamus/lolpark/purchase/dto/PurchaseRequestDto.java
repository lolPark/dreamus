package com.dreamus.lolpark.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseRequestDto {

    @NotNull
    private Integer orderId;

    @NotNull
    private Integer productId;

    @NotNull
    private Integer userId;

    @Min(0)
    @NotNull
    private Integer price;
}
