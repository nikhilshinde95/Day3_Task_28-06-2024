package com.org.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.org.entities.Category;
import com.org.entities.Subcategory;
import com.org.services.CategoryService;
import com.org.services.SubcategoryService;

@RestController
public class SubcategoryController {

    private static final Logger logger = LoggerFactory.getLogger(SubcategoryController.class);

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/subcategories")
    public List<Subcategory> getAllSubcategories() {
        logger.info("Fetching all subcategories");
        return subcategoryService.getAllSubcategories();
    }

    @GetMapping("/subcategories/{subcategoryId}")
    public ResponseEntity<Subcategory> getSubcategoryById(@PathVariable("subcategoryId") Long subcategoryId) {
        try {
            logger.info("Fetching subcategory with id: {}", subcategoryId);
            Subcategory subcategory = subcategoryService.getSubcategoryById(subcategoryId);
            if (subcategory != null) {
                return ResponseEntity.ok().body(subcategory);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching subcategory with id: {}. Error message: {}", subcategoryId,
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/subcategories")
    public ResponseEntity<Subcategory> createSubcategory(@RequestBody Subcategory subcategory) {
        try {
            logger.info("Creating subcategory: {}", subcategory);
            Category category = categoryService.getCategoryById(subcategory.getCategory().getId());
            if (category == null) {
                logger.error("Category with id {} not found", subcategory.getCategory().getId());
                return ResponseEntity.notFound().build();
            }
            subcategory.setCategory(category);

            Subcategory createdSubcategory = subcategoryService.createSubcategory(subcategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSubcategory);
        } catch (Exception e) {
            logger.error("Error occurred while creating subcategory. Error message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/subcategories/{subcategoryId}")
    public ResponseEntity<Subcategory> updateSubcategory(@PathVariable("subcategoryId") Long subcategoryId,
            @RequestBody Subcategory subcategory) {
        try {
            subcategory.setId(subcategoryId);
            logger.info("Updating subcategory with id: {}", subcategoryId);

            Category category = categoryService.getCategoryById(subcategory.getCategory().getId());
            if (category == null) {
                logger.error("Category with id {} not found", subcategory.getCategory().getId());
                return ResponseEntity.notFound().build();
            }
            subcategory.setCategory(category);

            Subcategory updatedSubcategory = subcategoryService.updateSubcategory(subcategory);
            if (updatedSubcategory != null) {
                return ResponseEntity.ok().body(updatedSubcategory);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while updating subcategory with id: {}. Error message: {}", subcategoryId,
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/subcategories/{subcategoryId}")
    public ResponseEntity<HttpStatus> deleteSubcategory(@PathVariable("subcategoryId") Long subcategoryId) {
        try {
            logger.info("Deleting subcategory with id: {}", subcategoryId);
            boolean isDeleted = subcategoryService.deleteSubcategory(subcategoryId);
            if (isDeleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while deleting subcategory with id: {}. Error message: {}", subcategoryId,
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
