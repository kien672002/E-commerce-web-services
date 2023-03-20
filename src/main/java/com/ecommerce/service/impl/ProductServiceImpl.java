package com.ecommerce.service.impl;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import com.ecommerce.util.FileUploadUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ProductDTO save(ProductDTO productDTO, MultipartFile image) throws EntityNotFoundException, IOException {
        Optional<Category> foundCategory = categoryRepository.findById(productDTO.getCategoryId());
        if (foundCategory.isEmpty())
            throw new EntityNotFoundException("Category with id=" + productDTO.getCategoryId());

        String fileName = FileUploadUtil.getInstance().saveFile(image);
        productDTO.setImageName(fileName);

        Product product = ProductMapper.toEntity(productDTO, foundCategory.get());
        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) throws EntityNotFoundException {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isEmpty())
            throw new EntityNotFoundException("Product not found with id=" + id);
        return foundProduct.map(ProductMapper::toDTO).get();
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        productRepository.deleteById(this.findById(id).getId());
    }
}
