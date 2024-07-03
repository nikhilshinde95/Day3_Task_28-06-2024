package com.org.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.dao.SubcategoryRepository;
import com.org.entities.Subcategory;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
	
	
	private static final Logger log = LoggerFactory.getLogger(SubcategoryServiceImpl.class);

	@Autowired
	private SubcategoryRepository subcategoryRepository;
	

	@Override
	public List<Subcategory> getAllSubcategories() {
		log.info("Fetching All Subcategories...");
		List<Subcategory> results = subcategoryRepository.findAll()
		        .stream()
		        .filter(Objects::nonNull) // Filter out null values by using stream API
		        .collect(Collectors.toList());
		
		return results;
	}

	@Override
	public Subcategory getSubcategoryById(Long id) {
		
		log.info("Fetching Subcategory By {} ID ",id);
		
		Optional<Subcategory> subcategory = subcategoryRepository.findById(id);
		
		return subcategory.orElseThrow(() -> new RuntimeException("Subcategory not found"));
	}

	@Override
	public Subcategory createSubcategory(Subcategory subcategory) {
		log.info("Adding New Subcategory in Database...");
		Subcategory saveSubcategories = subcategoryRepository.save(subcategory);
		log.info("New Subcategory is Added Successfully..");
		return saveSubcategories;
	}

	@Override
	public Subcategory updateSubcategory(Subcategory subcategory) {
		
		log.info("Updating Existing Subcategories..");
		Subcategory updateSubcategories = subcategoryRepository.save(subcategory);
		log.info("Subcategory is Updated Successfully...");
		return updateSubcategories;
	}

	@Override
	public boolean deleteSubcategory(Long id) {
		
		log.info("Deleting Subcategories by using Id {} ",id);
		Optional<Subcategory> subcategory = subcategoryRepository.findById(id);
		
		if (subcategory.isPresent()) {
			
			log.debug("Subcategory with Id {} is Deleted Successfully..",id);
			subcategoryRepository.delete(subcategory.get());
			return true;
		} else {
			log.error("Subcategory is Not Found.");
			throw new RuntimeException("Subcategory not found");
		}
	}
}
