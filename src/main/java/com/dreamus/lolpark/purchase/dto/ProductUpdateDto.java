package com.dreamus.lolpark.purchase.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductUpdateDto {

    @NotNull
    private Integer price;


    private Boolean isDel;
}
