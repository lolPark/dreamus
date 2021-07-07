package com.dreamus.lolpark.purchase.service;

import com.dreamus.lolpark.purchase.domain.Purchase;
import com.dreamus.lolpark.purchase.dto.PurchaseProductSumDto;
import com.dreamus.lolpark.purchase.dto.PurchaseRequestDto;
import com.dreamus.lolpark.purchase.dto.PurchaseUserSumDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseService {

    Purchase getPurchaseById(Integer id);

    Purchase createPurchase(PurchaseRequestDto purchaseRequestDto);

    Page<PurchaseUserSumDto> sumByUser(Pageable pageable);

    Page<PurchaseProductSumDto> sumByProduct(Pageable pageable);
}
