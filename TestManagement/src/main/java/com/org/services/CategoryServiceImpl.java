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
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryById(Long id) {
		log.info("Fetching Category With ID {}",id);
		Optional<Category> category = categoryRepository.findById(id);
		
		if(category.isPresent()) {
			return category.get();
		}else {
			String errorMessage = "Question not found with id: " + id;
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
		}
		
	}

	@Override
	public Category createCategory(Category category) {
		log.info("Adding New Category..");
		Category saveCategory = categoryRepository.save(category);
		return saveCategory;
	}

	@Override
	public Category updateCategory(Category category) {
		log.info("Updating Existing Category..");
		Category updateCategory = categoryRepository.save(category);
		return updateCategory;
	}

	@Override
	public boolean deleteCategory(Long id) {
		try {
			log.info("Deleting Existing Category with Id {} ",id);
			
			Optional<Category> category = categoryRepository.findById(id);
			if (category.isPresent()) {
				categoryRepository.delete(category.get());
				return true;
			} else {
				throw new RuntimeException("Category not found");
			}
		}catch(NumberFormatException e) {
			 log.error("Error occurred while deleting category with id: {}. Error message: {}", id, e.getMessage());
	            throw new RuntimeException("Error occurred while deleting category with id: " + id, e);
		}
	}
}
