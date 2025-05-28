package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.exception.ProductNotFoundException;
import com.matrix.SHOPPE.mapper.ProductMapper;
import com.matrix.SHOPPE.model.dto.ProductAddRequestDto;
import com.matrix.SHOPPE.model.dto.ProductBriefDto;
import com.matrix.SHOPPE.model.dto.ProductDto;
import com.matrix.SHOPPE.model.entity.Category;
import com.matrix.SHOPPE.model.entity.Product;
import com.matrix.SHOPPE.repository.CategoryRepository;
import com.matrix.SHOPPE.repository.ColourRepository;
import com.matrix.SHOPPE.repository.MaterialRepository;
import com.matrix.SHOPPE.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ColourRepository colourRepository;
    @Mock
    private MaterialRepository materialRepository;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;
    private ProductDto testProductDto;
    private ProductBriefDto testProductBriefDto;
    private ProductAddRequestDto testProductAddRequestDto;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1);
        testProduct.setProductName("Test Product");

        testProductDto = new ProductDto();
        testProductDto.setId(1);
        testProductDto.setProductName("Test Product");

        testProductBriefDto = new ProductBriefDto();
        testProductBriefDto.setId(1);
        testProductBriefDto.setProductName("Test Product");

        testCategory = new Category();
        testCategory.setId(1);

        testProductAddRequestDto = new ProductAddRequestDto();
        testProductAddRequestDto.setProductName("Test Product");
        testProductAddRequestDto.setCategoryId(1);
    }

    @Test
    void getProducts_ShouldReturnPageOfProductBriefDto() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> products = Collections.singletonList(testProduct);
        Page<Product> productPage = new PageImpl<>(products);

        when(productRepository.findAll(pageable)).thenReturn(productPage);
        when(productMapper.productToProductBriefDto(any(Product.class))).thenReturn(testProductBriefDto);

        Page<ProductBriefDto> result = productService.getProducts(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(productRepository).findAll(pageable);
    }

    @Test
    void searchProducts_ShouldReturnFilteredProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> products = Collections.singletonList(testProduct);
        Page<Product> productPage = new PageImpl<>(products);

        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(productPage);
        when(productMapper.productToProductBriefDto(any(Product.class))).thenReturn(testProductBriefDto);

        Page<ProductBriefDto> result = productService.searchProducts(
                10.0, 100.0, 4.0, "test", 1, true, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(productRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void createProduct_ShouldCreateNewProduct() {
        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));
        when(productMapper.productAddRequestToProduct(testProductAddRequestDto)).thenReturn(testProduct);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        when(productMapper.productToProductDto(testProduct)).thenReturn(testProductDto);

        ProductDto result = productService.createProduct(testProductAddRequestDto);

        assertNotNull(result);
        assertEquals(testProductDto.getId(), result.getId());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldUpdateExistingProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(productMapper.updateProduct(any(), any())).thenReturn(testProduct);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        when(productMapper.productToProductDto(testProduct)).thenReturn(testProductDto);

        ProductDto result = productService.updateProduct(testProductAddRequestDto, 1);

        assertNotNull(result);
        assertEquals(testProductDto.getId(), result.getId());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () ->
                productService.updateProduct(testProductAddRequestDto, 1)
        );
    }

    @Test
    void delete_ShouldDeleteProduct() {
        when(productRepository.existsById(1)).thenReturn(true);

        productService.delete(1);

        verify(productRepository).deleteById(1);
    }

    @Test
    void delete_ShouldThrowExceptionWhenProductNotFound() {
        when(productRepository.existsById(1)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () ->
                productService.delete(1)
        );
    }

    @Test
    void getProductById_ShouldReturnProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(productMapper.productToProductDto(testProduct)).thenReturn(testProductDto);

        ProductDto result = productService.getProductById(1);

        assertNotNull(result);
        assertEquals(testProductDto.getId(), result.getId());
    }

    @Test
    void getProductById_ShouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () ->
                productService.getProductById(1)
        );
    }
}