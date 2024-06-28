package com.org.services;

import java.util.List;

import com.org.entities.Subcategory;

public interface SubcategoryService {
	
	List<Subcategory> getAllSubcategories();

	Subcategory getSubcategoryById(Long id);

	Subcategory createSubcategory(Subcategory subcategory);

	Subcategory updateSubcategory(Subcategory subcategory);

	boolean deleteSubcategory(Long id);
}
