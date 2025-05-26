package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.CategoryRepository;
import com.matrix.SHOPPE.Repository.ColourRepository;
import com.matrix.SHOPPE.Repository.MaterialRepository;
import com.matrix.SHOPPE.Repository.ProductRepository;
import com.matrix.SHOPPE.exception.ProductNotFoundException;
import com.matrix.SHOPPE.mapper.ProductMapper;
import com.matrix.SHOPPE.model.dto.ProductAddRequestDto;
import com.matrix.SHOPPE.model.dto.ProductBriefDto;
import com.matrix.SHOPPE.model.dto.ProductDto;
import com.matrix.SHOPPE.model.entity.*;
import com.matrix.SHOPPE.service.ProductService;
import com.matrix.SHOPPE.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final ColourRepository colourRepository;
    private final MaterialRepository materialRepository;

    @Override
    public Page<ProductBriefDto> getProducts(Pageable pageable) {
        log.info("Fetching products page: {}", pageable.getPageNumber());
        return productRepository.findAll(pageable).map(productMapper::productToProductBriefDto);
    }

    @Override
    public Page<ProductBriefDto> searchProducts(
            Double minPrice,
            Double maxPrice,
            Double minRating,
            String namePattern,
            Integer categoryId,
            Boolean inStock,
            Pageable pageable) {

        log.info("Searching for products with filters: minPrice={}, maxPrice={}, minRating={}, namePattern={}, categoryId={}, inStock={}",
                minPrice, maxPrice, minRating, namePattern, categoryId, inStock);

        Specification<Product> spec = ProductSpecification.withDynamicQuery(
                minPrice, maxPrice, minRating, namePattern, categoryId, inStock);

        Page<Product> products = productRepository.findAll(spec, pageable);
        return products.map(productMapper::productToProductBriefDto);
    }

    @Override
    public ProductDto createProduct(ProductAddRequestDto productAddRequestDto) {
        log.info("Creating product: {}", productAddRequestDto.getProductName());
        Category category = categoryRepository.findById(productAddRequestDto.getCategoryId())
                .orElseThrow(() -> new ProductNotFoundException("Category with id " + productAddRequestDto.getCategoryId() + " not found"));

        Product product = productMapper.productAddRequestToProduct(productAddRequestDto);
        product.setCategory(category);

        if (productAddRequestDto.getProductInfo() != null) {
            ProductInfo productInfo = productAddRequestDto.getProductInfo();
            productInfo.setProduct(product);
            product.setProductInfo(productInfo);
        }

        if (productAddRequestDto.getColourNames() != null) {
            for (String colourName : productAddRequestDto.getColourNames()) {
                Colour colour = colourRepository.findByColourName(colourName)
                        .orElseGet(() -> {
                            Colour newColour = new Colour();
                            newColour.setColourName(colourName);
                            return colourRepository.save(newColour);
                        });
                product.addColour(colour);
            }
        }

        if (productAddRequestDto.getMaterialNames() != null) {
            for (String materialName : productAddRequestDto.getMaterialNames()) {
                Material material = materialRepository.findByMaterialName(materialName)
                        .orElseGet(() -> {
                            Material newMaterial = new Material();
                            newMaterial.setMaterialName(materialName);
                            return materialRepository.save(newMaterial);
                        });
                product.addMaterial(material);
            }
        }

        Product savedProduct = productRepository.save(product);

        productRepository.flush();
        log.info("Successfully created product with ID: {}", savedProduct.getId());
        return productMapper.productToProductDto(savedProduct);
    }


    @Override
    public ProductDto updateProduct(ProductAddRequestDto productAddRequestDto, Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        Product newProduct = productMapper.updateProduct(productAddRequestDto, product);
        return productMapper.productToProductDto(productRepository.save(newProduct));
    }

    @Override
    public void delete(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getProductById(Integer id) {
        return productMapper.productToProductDto(productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found")));
    }
}