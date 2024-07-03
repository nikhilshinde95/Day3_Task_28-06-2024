package com.org.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.dao.CategoryRepository;
import com.org.entities.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	
    private static final Logger log = LoggerFactory.getLogger(MCQQuestionServiceImpl.class);


	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() {
		log.info("Fetching ALL Categories");
		List<Category> list = categoryRepository.findAll();
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
			String errorMessage = "Question not found with id: " + id;
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
		}
		
	}

	@Override
	public Category createCategory(Category category) {
	    log.info("Adding New Category: {}", category);

	    // Check if a category with the same name and description already exists
	    Optional<Category> existingCategory = categoryRepository.findByNameandFindByDescription(category.getName(), category.getDescription());
	    
	    // If category already exists, throw an exception
	    if (existingCategory.isPresent()) {
	    	log.warn("Entered Name and Description is Already Exists in Database..");
	    	return new Category();
	    } else {
	        // Save the new category if it doesn't exist
	        Category savedCategory = categoryRepository.save(category);
	        log.info("Category Added Successfully: {}", savedCategory);
	        return savedCategory;
	    }
	}


	@Override
	public Category updateCategory(Category category) {
		log.info("Updating Existing Category..");
		Category updateCategory = categoryRepository.save(category);
		log.info("Category is Updated Successfully...");
		return updateCategory;
	}

	@Override
	public boolean deleteCategory(Long id) {
		try {
			log.info("Deleting Existing Category with Id {} ",id);
			
			Optional<Category> category = categoryRepository.findById(id);
			if (category.isPresent()) {
				log.debug("Category with Id {} is Deleted Successfully.",id);
				categoryRepository.delete(category.get());
				return true;
			} else {
				log.error("Category is Not Found.");
				throw new RuntimeException("Category not found");
			}
		}catch(NumberFormatException e) {
			 log.error("Error occurred while deleting category with id: {}. Error message: {}", id, e.getMessage());
	            throw new RuntimeException("Error occurred while deleting category with id: " + id, e);
		}
	}
}
