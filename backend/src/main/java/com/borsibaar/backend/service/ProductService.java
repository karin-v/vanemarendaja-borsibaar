package com.borsibaar.backend.service;

import com.borsibaar.backend.dto.ProductRequest;
import com.borsibaar.backend.dto.ProductResponse;
import com.borsibaar.backend.entity.Category;
import com.borsibaar.backend.entity.Product;
import com.borsibaar.backend.mapper.ProductMapper;
import com.borsibaar.backend.repository.CategoryRepository;
import com.borsibaar.backend.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Category cat = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Category not found: " + request.categoryId()));

        Product entity = productMapper.toEntity(request);

        // TODO: replace with real tenant/org resolver (e.g., from JWT/subdomain)
        Long orgId = 1L;
        entity.setOrganizationId(orgId);
        entity.setActive(true);

        String normalizedName = entity.getName() != null ? entity.getName().trim() : null;
        if (normalizedName == null || normalizedName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name must not be blank");
        }
        entity.setName(normalizedName);

        if (!orgId.equals(cat.getOrganizationId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category does not belong to the organization");
        }

        if (productRepository.existsByOrganizationIdAndNameIgnoreCase(orgId, normalizedName)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Product with name '" + normalizedName + "' already exists");
        }

        Product saved = productRepository.save(entity);

        ProductResponse base = productMapper.toResponse(saved);
        return new ProductResponse(
                base.id(),
                base.name(),
                base.description(),
                base.currentPrice(),
                base.categoryId(),
                cat.getName()
        );
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found: " + id));

        ProductResponse base = productMapper.toResponse(product);
        String categoryName = categoryRepository.findById(product.getCategoryId())
                .map(Category::getName)
                .orElse(null);

        return new ProductResponse(
                base.id(),
                base.name(),
                base.description(),
                base.currentPrice(),
                base.categoryId(),
                categoryName
        );
    }
}
