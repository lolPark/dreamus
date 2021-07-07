package com.dreamus.lolpark.purchase.service.impl;

import com.dreamus.lolpark.purchase.constants.MessageConstants;
import com.dreamus.lolpark.purchase.domain.Product;
import com.dreamus.lolpark.purchase.repository.ProductRepository;
import com.dreamus.lolpark.purchase.service.ProductService;
import com.dreamus.lolpark.purchase.support.exception.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> getPagedProducts(Pageable pageable) {
        return productRepository.findAllByIsDel(false, pageable);
    }

    @Cacheable(value = "product", unless = "#result == null")
    @Override
    public Product getProduct(Integer id) {
        return productRepository.findByIdAndIsDel(id, false)
            .orElseThrow(() -> new ResourceNotFoundedException(MessageConstants.NOT_VALID_PRODUCT));
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @CacheEvict(value = "product", key = "#id")
    @Transactional
    @Override
    public Product delete(Integer id) {
        Product product = this.getProduct(id);
        product.setIsDel(true);
        return productRepository.save(product);
    }

    @CachePut(value = "product", key = "#id")
    @Transactional
    @Override
    public Product update(Integer id, Product product) {

        Product updateProduct = getProduct(id);
        updateProduct.setPrice(product.getPrice());
        updateProduct.setIsDel(product.getIsDel());

        return productRepository.save(updateProduct);
    }
}
