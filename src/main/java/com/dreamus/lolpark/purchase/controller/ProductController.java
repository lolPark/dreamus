package com.dreamus.lolpark.purchase.controller;

import com.dreamus.lolpark.purchase.domain.Product;
import com.dreamus.lolpark.purchase.dto.PagedMetaDto;
import com.dreamus.lolpark.purchase.dto.ProductCreateDto;
import com.dreamus.lolpark.purchase.dto.ProductUpdateDto;
import com.dreamus.lolpark.purchase.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> getPagedProduct(Pageable pageable) {

        Page<Product> pagedProducts = productService.getPagedProducts(pageable);

        PagedMetaDto<Product> pagedMetaDto = new PagedMetaDto<>(pagedProducts.getContent(), new PagedModel.PageMetadata(pagedProducts.getSize(), pagedProducts.getNumber(),
            pagedProducts.getTotalElements(), pagedProducts.getTotalPages()));

        return ResponseEntity.ok(pagedMetaDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Integer id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductCreateDto productCreateDto) {
        Product savedProduct = productService.save(Product.builder()
            .name(productCreateDto.getName())
            .price(productCreateDto.getPrice())
            .build()
        );

        Link link = linkTo(ProductController.class)
            .slash(savedProduct.getId()).withSelfRel();

        return ResponseEntity
            .created(link.toUri())
            .body(EntityModel.of(savedProduct, link)
            );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductUpdateDto productUpdateDto) {

        Product updatedProduct = productService.update(id, Product.builder()
            .price(productUpdateDto.getPrice())
            .isDel(productUpdateDto.getIsDel())
            .build());

        return ResponseEntity.ok(EntityModel.of(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {

        Product deletedProduct = productService.delete(id);

        return ResponseEntity.ok(EntityModel.of(deletedProduct));
    }
}
