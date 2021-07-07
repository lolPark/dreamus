package com.dreamus.lolpark.purchase.repository;

import com.dreamus.lolpark.purchase.dto.PurchaseProductSumDto;
import com.dreamus.lolpark.purchase.dto.PurchaseUserSumDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseRepositoryCustom {

    Page<PurchaseUserSumDto> groupByUser(Pageable pageable);

    Page<PurchaseProductSumDto> groupByProduct(Pageable pageable);
}
