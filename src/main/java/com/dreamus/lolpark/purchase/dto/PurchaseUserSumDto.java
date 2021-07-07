package com.dreamus.lolpark.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseUserSumDto {

    private Integer userId;

    private String userName;

    private Long productCount;

    private Integer productPriceSum;
}
