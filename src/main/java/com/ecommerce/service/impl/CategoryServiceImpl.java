package com.ecommerce.service.impl;

import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.mapper.CategoryMapper;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) throws EntityNotFoundException, DataIntegrityViolationException {
        Category category;

        if (categoryDTO.getId() != null) {
            Optional<Category> oldCategory = categoryRepository.findById(categoryDTO.getId());
            if (oldCategory.isEmpty())
                throw new EntityNotFoundException("Category not found with id=" + categoryDTO.getId());
            category = CategoryMapper.toEntity(categoryDTO, oldCategory.get().getProducts());
        } else
            category = CategoryMapper.toEntity(categoryDTO, new ArrayList<>());

        return CategoryMapper.toDTO(categoryRepository.save(category));

    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findByName(String name) {
        Optional<Category> foundCategory = categoryRepository.findByName(name);
        if (foundCategory.isEmpty())
            throw new EntityNotFoundException("Category not found with name=" + name);
        return foundCategory.map(CategoryMapper::toDTO).get();
    }

    @Override
    public CategoryDTO findById(Long id) throws EntityNotFoundException {
        Optional<Category> foundCategory = categoryRepository.findById(id);
        if (foundCategory.isEmpty())
            throw new EntityNotFoundException("Category not found with id=" + id);
        return foundCategory.map(CategoryMapper::toDTO).get();
    }


    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        categoryRepository.deleteById(this.findById(id).getId());
    }
}
