package com.org.services.serviceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.dao.CategoryRepository;
import com.org.dao.SubcategoryRepository;
import com.org.entities.Category;
import com.org.entities.Subcategory;
import com.org.exception.CategoryNotFoundException;
import com.org.exception.DuplicateDataFound;
import com.org.exception.EmptyDatabaseException;
import com.org.services.SubcategoryService;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
	
	
	private static final Logger log = LoggerFactory.getLogger(SubcategoryServiceImpl.class);

	@Autowired
	private SubcategoryRepository subcategoryRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	

	@Override
	public List<Subcategory> getAllSubcategories() {
		log.info("Fetching All Subcategories...");
		List<Subcategory> results = subcategoryRepository.findAll()
		        .stream()
		        .filter(Objects::nonNull) // Filter out null values by using stream API
		        .collect(Collectors.toList());
		
		if(results.isEmpty()) {
			log.warn("No Subcategory is found in database");
			throw new EmptyDatabaseException("No Subcategories found in database");
		}
		
		return results;
	}

	@Override
	public Subcategory getSubcategoryById(Long id) {
		
		log.info("Fetching Subcategory By {} ID ",id);
		
		Optional<Subcategory> subcategory = subcategoryRepository.findById(id);
		
		if(subcategory.isEmpty()) {
			log.debug("Subcategory ID is Not Found");
			throw new CategoryNotFoundException("Subategory Id is Not Present in Database");
		}
		log.info("Successfully Subcategory is Fetched.");
		return subcategory.get();
	}

	@Override
	public Subcategory createSubcategory(Subcategory subcategory) {
		log.info("Adding New Subcategory in Database...");
		
		Optional<Subcategory> byNameandFindByDescription = subcategoryRepository.findByNameandDescription(subcategory.getName(), subcategory.getDescription());
		if(byNameandFindByDescription.isPresent()) {
			throw new DuplicateDataFound("Subcategory is Already Exits in Database");
	    } 
	        // Save the new category if it doesn't exist
		Subcategory saveSubcategory= subcategoryRepository.save(subcategory);
        log.info("Subategory Added Successfully: {}", saveSubcategory);
        return saveSubcategory;
	}

	@Override
	public Subcategory updateSubcategory(Subcategory subcategory) {
		
		log.info("Updating Existing Subcategories..");
		 Optional<Category> category = categoryRepository.findById(subcategory.getCategory().getId());
         if (category == null) {
             log.error("Category with id {} not found", subcategory.getCategory().getId());
            throw new CategoryNotFoundException("Subcategory is Not Found.");
         }
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
		}
			log.error("Subcategory is Not Found.");
			throw new CategoryNotFoundException("Subcategory is not found in database");
		
	}
}
