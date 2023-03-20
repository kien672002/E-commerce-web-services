package com.ecommerce.mapper;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;

public class ProductMapper {
    static public ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setImageName(product.getImageName());
        productDTO.setDescription(product.getDescription());
        if (product.getCategory() != null)
            productDTO.setCategoryId(product.getCategory().getId());
        return productDTO;
    }

    static public Product toEntity(ProductDTO productDTO, Category category) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setImageName(productDTO.getImageName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);
        return product;
    }
}
