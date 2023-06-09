package com.ecommerce.mapper;

import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;

import java.util.List;

public class CategoryMapper {
    static public CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    static public Category toEntity(CategoryDTO categoryDTO, List<Product> products) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setProducts(products);
        return category;
    }
}
