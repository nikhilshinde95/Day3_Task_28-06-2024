package com.org.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.org.entities.Category;
import com.org.services.CategoryService;

@RestController
public class CategoryController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories")
	public List<Category> getAllCategories() {
		logger.info("Fetching all categories");
		List<Category> allCategories = categoryService.getAllCategories();
		return allCategories;
	}

	@GetMapping("/categories/{categoryId}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("categoryId") Long categoryId) {
		try {
			logger.info("Fetching category with id: {}", categoryId);
			Category category = categoryService.getCategoryById(categoryId);
			return ResponseEntity.ok().body(category);
		} catch (Exception e) {
			logger.error("Error occurred while fetching category with id: {}. Error message: {}", categoryId, e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/categories")
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		try {
			logger.info("Creating category: {}", category);
			Category createdCategory = categoryService.createCategory(category);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
		} catch (Exception e) {
			logger.error("Error occurred while creating category. Error message: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/categories/{categoryId}")
	public ResponseEntity<Category> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody Category category) {
		try {
			logger.info("Updating category with id: {}", categoryId);
			category.setId(categoryId);
			Category updatedCategory = categoryService.updateCategory(category);
			return ResponseEntity.ok().body(updatedCategory);
		} catch (Exception e) {
			logger.error("Error occurred while updating category with id: {}. Error message: {}", categoryId, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/categories/{categoryId}")
	public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("categoryId") Long categoryId) {
		try {
			logger.info("Deleting category with id: {}", categoryId);
			boolean isDeleted = categoryService.deleteCategory(categoryId);
			if (isDeleted) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			logger.error("Error occurred while deleting category with id: {}. Error message: {}", categoryId, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
