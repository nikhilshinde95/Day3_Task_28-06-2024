package com.org.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.dao.SubcategoryRepository;
import com.org.entities.Subcategory;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

	@Autowired
	private SubcategoryRepository subcategoryRepository;

	@Override
	public List<Subcategory> getAllSubcategories() {
//		return subcategoryRepository.findAll();
		List<Subcategory> results = subcategoryRepository.findAll()
		        .stream()
		        .filter(Objects::nonNull) // Filter out null values
		        .collect(Collectors.toList());
		
		return results;
	}

	@Override
	public Subcategory getSubcategoryById(Long id) {
		Optional<Subcategory> subcategory = subcategoryRepository.findById(id);
		return subcategory.orElseThrow(() -> new RuntimeException("Subcategory not found"));
	}

	@Override
	public Subcategory createSubcategory(Subcategory subcategory) {
		return subcategoryRepository.save(subcategory);
	}

	@Override
	public Subcategory updateSubcategory(Subcategory subcategory) {
		return subcategoryRepository.save(subcategory);
	}

	@Override
	public boolean deleteSubcategory(Long id) {
		Optional<Subcategory> subcategory = subcategoryRepository.findById(id);
		if (subcategory.isPresent()) {
			subcategoryRepository.delete(subcategory.get());
			return true;
		} else {
			throw new RuntimeException("Subcategory not found");
		}
	}
}
