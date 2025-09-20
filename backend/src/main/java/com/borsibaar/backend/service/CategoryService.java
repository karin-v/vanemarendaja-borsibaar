package com.borsibaar.backend.service;

import com.borsibaar.backend.dto.CategoryRequest;
import com.borsibaar.backend.dto.CategoryResponse;
import com.borsibaar.backend.entity.Category;
import com.borsibaar.backend.mapper.CategoryMapper;
import com.borsibaar.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryResponse categoryResponse(CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);

        // TODO: replace with your tenant/org provider (e.g., from JWT claim)
        Long orgId = 1L; // ← temporary: hard-code for local testing
        category.setOrganizationId(orgId);

        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }
}
