package com.dreamus.lolpark.purchase.repository;

import com.dreamus.lolpark.purchase.domain.Purchase;
import com.dreamus.lolpark.purchase.support.jpa.JpaQueryDslPredicateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

public interface PurchaseRepository extends JpaQueryDslPredicateRepository<Purchase, Integer>, PurchaseRepositoryCustom {
}
