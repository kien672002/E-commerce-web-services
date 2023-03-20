package com.ecommerce.service;

import com.ecommerce.dto.CategoryDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface CategoryService {
    CategoryDTO save(CategoryDTO categoryDTO) throws DataIntegrityViolationException, EntityNotFoundException;

    List<CategoryDTO> findAll();

    CategoryDTO findByName(String name) throws EntityNotFoundException;

    CategoryDTO findById(Long id) throws EntityNotFoundException;

    void deleteById(Long id) throws EntityNotFoundException;
}
