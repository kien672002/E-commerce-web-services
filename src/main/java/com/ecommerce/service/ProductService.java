package com.ecommerce.service;

import com.ecommerce.dto.ProductDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDTO save(ProductDTO productDTO, MultipartFile image) throws EntityNotFoundException, IOException;

    List<ProductDTO> findAll();

    ProductDTO findById(Long id) throws EntityNotFoundException;

    void deleteById(Long id) throws EntityNotFoundException;
}
