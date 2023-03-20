package com.ecommerce.controller;

import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.model.ResponseObject;
import com.ecommerce.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getCategories() {
        List<CategoryDTO> foundCategories = categoryService.findAll();
        return ResponseEntity
                .ok()
                .body(new ResponseObject("Query all categories successfully", foundCategories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategory(@PathVariable Long id) {
        try {
            CategoryDTO foundCategory = categoryService.findById(id);
            return ResponseEntity
                    .ok()
                    .body(new ResponseObject("Category with id=" + id + " found", foundCategory));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(e.getMessage(), null));
        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> save(@RequestBody CategoryDTO categoryDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseObject(categoryDTO.getId() != null ? "Update" : "Create" + " category successfully", categoryService.save(categoryDTO)));

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ResponseObject("Category name=" + categoryDTO.getName().strip() + " exited", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateById(@PathVariable Long id,
                                              @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        return this.save(categoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        try {
            categoryService.deleteById(id);
            return ResponseEntity
                    .ok()
                    .body(new ResponseObject("Remove category successfully", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(e.getMessage(), null));
        }


    }

}
