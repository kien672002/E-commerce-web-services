package com.ecommerce.controller;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.ResponseObject;
import com.ecommerce.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    final
    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    ResponseEntity<ResponseObject> findAll() {
        List<ProductDTO> foundProducts = productService.findAll();
        return ResponseEntity
                .ok()
                .body(new ResponseObject("Query all product successfully", foundProducts));
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        try {
            ProductDTO foundProduct = productService.findById(id);
            return ResponseEntity
                    .ok()
                    .body(new ResponseObject("Query product with id=" + id + " successfully", foundProduct));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(e.getMessage(), null));

        }
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping("")
    ResponseEntity<ResponseObject> save(@ModelAttribute ProductDTO productDTO,
                                        @RequestParam MultipartFile image) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseObject(productDTO.getId() != null ? "Update" : "Add" + " product successfully", productService.save(productDTO, image)));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(e.getMessage(), null));
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("Failed to save file: " + e.getMessage(), null));
        }
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> update(@PathVariable Long id,
                                          @ModelAttribute ProductDTO productDTO,
                                          @RequestParam MultipartFile image) {
        productDTO.setId(id);
        return this.save(productDTO, image);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteById(@PathVariable Long id) {
        try {
            productService.deleteById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(e.getMessage(), null));
        }

        return ResponseEntity
                .ok()
                .body(new ResponseObject("Remove product successfully", null));
    }
}
