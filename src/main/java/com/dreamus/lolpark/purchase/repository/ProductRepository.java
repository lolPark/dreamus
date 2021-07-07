package com.dreamus.lolpark.purchase.repository;

import com.dreamus.lolpark.purchase.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAllByIsDel(Boolean isDel, Pageable pageable);

    Optional<Product> findByIdAndIsDel(Integer id, Boolean isDel);
}
