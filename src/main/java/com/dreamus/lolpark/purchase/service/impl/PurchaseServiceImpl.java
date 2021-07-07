package com.dreamus.lolpark.purchase.service.impl;

import com.dreamus.lolpark.purchase.constants.MessageConstants;
import com.dreamus.lolpark.purchase.domain.Product;
import com.dreamus.lolpark.purchase.domain.Purchase;
import com.dreamus.lolpark.purchase.domain.User;
import com.dreamus.lolpark.purchase.dto.PurchaseProductSumDto;
import com.dreamus.lolpark.purchase.dto.PurchaseRequestDto;
import com.dreamus.lolpark.purchase.dto.PurchaseUserSumDto;
import com.dreamus.lolpark.purchase.repository.PurchaseRepository;
import com.dreamus.lolpark.purchase.service.ProductService;
import com.dreamus.lolpark.purchase.service.PurchaseService;
import com.dreamus.lolpark.purchase.service.UserService;
import com.dreamus.lolpark.purchase.support.exception.PurchaseException;
import com.dreamus.lolpark.purchase.support.exception.ResourceNotFoundedException;
import com.dreamus.lolpark.purchase.type.PurchaseReasonCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final UserService userService;

    private final ProductService productService;

    @Override
    public Purchase getPurchaseById(Integer id) {
        return purchaseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundedException(MessageConstants.NO_PURCHASE_DATA));
    }

    @Transactional
    @Override
    public Purchase createPurchase(PurchaseRequestDto purchaseRequestDto) {

        User user;
        Product product;

        try{
            user = userService.getUser(purchaseRequestDto.getUserId());
        } catch (ResourceNotFoundedException ex) {
            throw new PurchaseException(PurchaseReasonCode.NO_USER_DATA, ex.getMessage());
        }

        try{
            product = productService.getProduct(purchaseRequestDto.getProductId());
        } catch (ResourceNotFoundedException ex) {
            throw new PurchaseException(PurchaseReasonCode.NOT_VALID_PRODUCT, ex.getMessage());
        }

        if (!purchaseRequestDto.getPrice().equals(product.getPrice())) {
            PurchaseReasonCode reasonCode = PurchaseReasonCode.NOT_MATCHED_PRICE;
            throw new PurchaseException(reasonCode, reasonCode.getMessage());
        }

        Purchase purchase = Purchase.builder()
            .id(purchaseRequestDto.getOrderId())
            .user(user)
            .product(product)
            .price(purchaseRequestDto.getPrice())
            .build();

        return purchaseRepository.save(purchase);
    }

    @Override
    public Page<PurchaseUserSumDto> sumByUser(Pageable pageable) {
        return purchaseRepository.groupByUser(pageable);
    }

    @Override
    public Page<PurchaseProductSumDto> sumByProduct(Pageable pageable) {
        return purchaseRepository.groupByProduct(pageable);
    }
}
