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
@RequestMapping("/api/categories")
public class CategoryController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@GetMapping()
	public List<Category> getAllCategories() {
		logger.info("Fetching all categories");
		List<Category> allCategories = categoryService.getAllCategories();
		logger.debug("All Categories are Fetched Successfully...");
		return allCategories;
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("categoryId") Long categoryId) {
		
			logger.info("Fetching category with id: {}", categoryId);
			Category category = categoryService.getCategoryById(categoryId);
			logger.debug("Category with ID is Fetched Successfully.");
			return ResponseEntity.ok().body(category);
		
	}

	@PostMapping()
	public ResponseEntity<Object> createCategory(@RequestBody Category category) {
			logger.info("Creating category: {}", category);
			Category createdCategory = categoryService.createCategory(category);
			logger.debug("New Category is Created Successfully..");
			return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
		
	}

	@PutMapping("/{categoryId}")
	public ResponseEntity<Category> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody Category category) {
			logger.info("Updating category with id: {}", categoryId);
			category.setId(categoryId);
			Category updatedCategory = categoryService.updateCategory(category);
			logger.debug("Category is Updated Successfully..");
			return ResponseEntity.ok().body(updatedCategory);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("categoryId") Long categoryId) {
			logger.info("Deleting category with id: {}", categoryId);
			boolean isDeleted = categoryService.deleteCategory(categoryId);
			logger.info("Category is Deleted Successfully.");
			return new ResponseEntity<>(HttpStatus.OK);
	}
}
