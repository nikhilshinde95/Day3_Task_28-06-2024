package com.org.services.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.dao.CategoryRepository;
import com.org.entities.Category;
import com.org.exception.CategoryNotFoundException;
import com.org.exception.DuplicateDataFound;
import com.org.exception.EmptyDatabaseException;
import com.org.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
    private static final Logger log = LoggerFactory.getLogger(MCQQuestionServiceImpl.class);


	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() {
		log.info("Fetching ALL Categories");
		List<Category> list = categoryRepository.findAll();
		if(list.isEmpty()) {
			throw new EmptyDatabaseException("No categories found in database...");
		}
		log.info("All Categories are Fetched Successfully..");
		return list;
	}

	@Override
	public Category getCategoryById(Long id) {
		
		log.info("Fetching Category With ID {}",id);
		Optional<Category> category = categoryRepository.findById(id);
		if(category.isPresent()) {
			log.debug("Category is Present.");
			return category.get();
		}else {
			log.error("Category is not found in database");
            throw new CategoryNotFoundException("You Entered Category ID is Not Found in Database");
		}
		
	}

	@Override
	public Category createCategory(Category category) {
	    log.info("Adding New Category: {}", category);

	    // Check if a category with the same name and description already exists
	    Optional<Category> existingCategory = categoryRepository.findByNameandDescription(category.getName(), category.getDescription());
	   
	    if (existingCategory.isPresent()) {
	    	log.warn("Entered Name and Description is Already Exists in Database..");
	    	throw new DuplicateDataFound("Entered Name and Description is Already Exists in Database..");
	    }
	    Category savedCategory = categoryRepository.save(category);
        log.debug("New Category Added Successfully: {}", savedCategory);
        return savedCategory;
	}

	@Override
	public Category updateCategory(Category category) {
		log.info("Updating Existing Category..");
		
		Optional<Category> categoryOpt = categoryRepository.findById(category.getId());
		if(categoryOpt.isPresent()) {
			Category updateCategory = categoryRepository.save(category);
			log.debug("Category is Updated Successfully...");
			return updateCategory;
		}
		log.error("Category Id is not found");
        throw new CategoryNotFoundException("ID is not present in database");
	}

	@Override
	public boolean deleteCategory(Long id) {
			log.info("Deleting Existing Category with Id {} ",id);
			
			Optional<Category> category = categoryRepository.findById(id);
			if (category.isPresent()) {
				log.debug("Category with Id {} is Deleted Successfully.",id);
				categoryRepository.delete(category.get());
				return true;
			} else {
				log.error("Category is not found in database.");
				throw new CategoryNotFoundException("Category is not found in database.");
			}
	}
}
