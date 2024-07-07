package com.org.services;

import java.util.List;

import com.org.entities.Category;

public interface CategoryService {
	
	public List<Category> getAllCategories();

	public Category getCategoryById(Long id);

	public Category createCategory(Category category);

	public Category updateCategory(Category category);

	public boolean deleteCategory(Long id);
}
