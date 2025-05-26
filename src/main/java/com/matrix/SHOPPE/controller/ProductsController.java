package com.matrix.SHOPPE.controller;


import com.matrix.SHOPPE.model.dto.ProductAddRequestDto;
import com.matrix.SHOPPE.model.dto.ProductBriefDto;
import com.matrix.SHOPPE.model.dto.ProductDto;
import com.matrix.SHOPPE.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductBriefDto> getProducts(Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    public Page<ProductBriefDto> searchProducts(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) String namePattern,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Boolean inStock,
            Pageable pageable) {
        return productService.searchProducts(minPrice, maxPrice, minRating, namePattern, categoryId, inStock, pageable);
    }


    @PostMapping
    public ProductDto create(@RequestBody ProductAddRequestDto productAddRequestDto) {
        return productService.createProduct(productAddRequestDto);
    }

    @PutMapping("/{id}")
    public ProductDto update(@RequestBody ProductAddRequestDto productAddRequestDto, @PathVariable Integer id) {
        return productService.updateProduct(productAddRequestDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }
}
