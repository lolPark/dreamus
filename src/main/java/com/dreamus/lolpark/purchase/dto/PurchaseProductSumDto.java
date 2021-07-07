package com.dreamus.lolpark.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseProductSumDto {

    private Integer productId;

    private String productName;

    private Long userCount;

    private Integer productPriceSum;
}
