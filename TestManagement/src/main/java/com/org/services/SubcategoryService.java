package com.org.services;

import java.util.List;

import com.org.entities.Subcategory;

public interface SubcategoryService {
	
	public List<Subcategory> getAllSubcategories();

	public Subcategory getSubcategoryById(Long id);

	public Subcategory createSubcategory(Subcategory subcategory);

	public Subcategory updateSubcategory(Subcategory subcategory);

	public boolean deleteSubcategory(Long id);
}
