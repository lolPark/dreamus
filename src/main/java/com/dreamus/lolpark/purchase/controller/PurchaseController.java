package com.dreamus.lolpark.purchase.controller;

import com.dreamus.lolpark.purchase.domain.Purchase;
import com.dreamus.lolpark.purchase.dto.PagedMetaDto;
import com.dreamus.lolpark.purchase.dto.PurchaseProductSumDto;
import com.dreamus.lolpark.purchase.dto.PurchaseRequestDto;
import com.dreamus.lolpark.purchase.dto.PurchaseUserSumDto;
import com.dreamus.lolpark.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("")
    public ResponseEntity<?> createPurchase(@Valid @RequestBody PurchaseRequestDto purchaseRequestDto) {
        Purchase purchase = purchaseService.createPurchase(purchaseRequestDto);
        Link link = linkTo(PurchaseController.class).slash(purchase.getId()).withSelfRel();
        return ResponseEntity.created(link.toUri())
            .body(EntityModel.of(purchase, link));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPurchase(@PathVariable Integer id) {
        Purchase purchase = purchaseService.getPurchaseById(id);
        return ResponseEntity.ok(EntityModel.of(purchase));
    }


    /**
     * 사용자 구매 합계
     * @param pageable
     * @return
     */
    @GetMapping("/sumByUser")
    public ResponseEntity<?> getPurchaseSumByUser(Pageable pageable) {
        Page<PurchaseUserSumDto> sumByUser = purchaseService.sumByUser(pageable);
        PagedMetaDto<PurchaseUserSumDto> pagedMetaDto = new PagedMetaDto<>(sumByUser.getContent(), new PagedModel.PageMetadata(sumByUser.getSize(), sumByUser.getNumber(),
            sumByUser.getTotalElements(), sumByUser.getTotalPages()));
        return ResponseEntity.ok(pagedMetaDto);
    }

    /**
     * 상품별 구매 합계
     * @param pageable
     * @return
     */
    @GetMapping("/sumByProduct")
    public ResponseEntity<?> getPurchaseSumByProduct(Pageable pageable) {
        Page<PurchaseProductSumDto> productSum = purchaseService.sumByProduct(pageable);
        PagedMetaDto<PurchaseProductSumDto> pagedMetaDto = new PagedMetaDto<>(productSum.getContent(),
            new PagedModel.PageMetadata(productSum.getSize(), productSum.getNumber(),
            productSum.getTotalElements(), productSum.getTotalPages()));
        return ResponseEntity.ok(pagedMetaDto);
    }
}
