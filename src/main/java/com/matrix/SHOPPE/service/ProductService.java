package com.matrix.SHOPPE.service;

import com.matrix.SHOPPE.model.dto.ProductAddRequestDto;
import com.matrix.SHOPPE.model.dto.ProductBriefDto;
import com.matrix.SHOPPE.model.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface ProductService {

    Page<ProductBriefDto> getProducts(Pageable pageable);

    ProductDto createProduct(@RequestBody ProductAddRequestDto productAddRequestDto);

    ProductDto updateProduct(@RequestBody ProductAddRequestDto productAddRequestDto, Integer id);

    void delete(@PathVariable Integer id);

    ProductDto getProductById(@PathVariable Integer id);

    Page<ProductBriefDto> searchProducts(
            Double minPrice,
            Double maxPrice,
            Double minRating,
            String namePattern,
            Integer categoryId,
            Boolean inStock,
            Pageable pageable
    );
}