package com.dreamus.lolpark.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.PagedModel;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagedMetaDto<T> {

    private List<T> contents;

    private PagedModel.PageMetadata page;
}
