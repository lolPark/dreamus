package com.dreamus.lolpark.purchase.repository.impl;

import com.dreamus.lolpark.purchase.domain.QProduct;
import com.dreamus.lolpark.purchase.domain.QPurchase;
import com.dreamus.lolpark.purchase.domain.QUser;
import com.dreamus.lolpark.purchase.dto.PurchaseProductSumDto;
import com.dreamus.lolpark.purchase.dto.PurchaseUserSumDto;
import com.dreamus.lolpark.purchase.repository.PurchaseRepositoryCustom;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

import static com.dreamus.lolpark.purchase.domain.QProduct.product;
import static com.dreamus.lolpark.purchase.domain.QPurchase.purchase;
import static com.dreamus.lolpark.purchase.domain.QUser.user;

@RequiredArgsConstructor
public class PurchaseRepositoryCustomImpl implements PurchaseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PurchaseUserSumDto> groupByUser(Pageable pageable) {

        QueryResults<Tuple> queryResult = queryFactory
            .select(user.id, user.name, product.count(), product.price.sum())
            .from(purchase)
            .leftJoin(user).on(purchase.user.eq(user))
            .leftJoin(product).on(purchase.product.eq(product))
            .orderBy(
                user.id.asc()
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .groupBy(user)
            .fetchResults();

        return new PageImpl<>(queryResult.getResults().stream().map(this::userSumDto)
            .collect(Collectors.toList()),
            pageable,
            queryResult.getTotal());
    }

    @Override
    public Page<PurchaseProductSumDto> groupByProduct(Pageable pageable) {

        QueryResults<Tuple> queryResult = queryFactory
            .select(product.id, product.name, user.count(), product.price.sum())
            .from(purchase)
            .leftJoin(user).on(purchase.user.eq(user))
            .leftJoin(product).on(purchase.product.eq(product))
            .orderBy(
                product.id.asc()
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .groupBy(product)
            .fetchResults();

        return new PageImpl<>(queryResult.getResults().stream().map(this::productSumDto)
            .collect(Collectors.toList()),
            pageable,
            queryResult.getTotal());
    }

    private PurchaseUserSumDto userSumDto(Tuple tuple) {
        return PurchaseUserSumDto.builder()
            .userId(tuple.get(0, Integer.class))
            .userName(tuple.get(1, String.class))
            .productCount(tuple.get(2, Long.class))
            .productPriceSum(tuple.get(3, Integer.class))
            .build();
    }

    private PurchaseProductSumDto productSumDto(Tuple tuple) {
        return PurchaseProductSumDto.builder()
            .productId(tuple.get(0, Integer.class))
            .productName(tuple.get(1, String.class))
            .userCount(tuple.get(2, Long.class))
            .productPriceSum(tuple.get(3, Integer.class))
            .build();
    }
}
