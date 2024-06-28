package com.org.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.org.entities.Category;
import com.org.services.CategoryService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
	
	
	@Mock
	private CategoryService categoryService;
	
	@InjectMocks
	private CategoryControllerTest categoryControllerTest;
	
	@Test
	void getAllCategories() {
		
		Category category = new Category(1L,"Java","Core Java category");
		
		
	}

}
