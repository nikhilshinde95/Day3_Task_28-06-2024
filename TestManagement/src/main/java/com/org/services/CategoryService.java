package com.org.services;

import java.util.List;

import com.org.entities.Category;

public interface CategoryService {
	List<Category> getAllCategories();

	Category getCategoryById(Long id);

	Category createCategory(Category category);

	Category updateCategory(Category category);

	boolean deleteCategory(Long id);
}
