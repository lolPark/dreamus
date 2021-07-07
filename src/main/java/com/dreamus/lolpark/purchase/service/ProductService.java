package com.dreamus.lolpark.purchase.service;

import com.dreamus.lolpark.purchase.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<Product> getPagedProducts(Pageable pageable);

    Product getProduct(Integer id);

    Product save(Product product);

    Product delete(Integer id);

    Product update(Integer id, Product product);
}
