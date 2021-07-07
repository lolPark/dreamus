package com.dreamus.lolpark.purchase.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductCreateDto extends ProductUpdateDto {

    @NotNull
    private String name;
}
