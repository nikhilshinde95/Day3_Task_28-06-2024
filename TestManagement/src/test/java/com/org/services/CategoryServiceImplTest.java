package com.org.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.org.dao.CategoryRepository;
import com.org.entities.Category;
import com.org.services.serviceImpl.CategoryServiceImpl;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@InjectMocks
	private CategoryServiceImpl categoryServiceImpl;
	
	private Category category1;
	private Category category2;
	private Category category3;
	
	@BeforeEach
	public void SetUp() {
		category1 = new Category(1L, "Java", "Core Java category");
	        category2 = new Category(2L, "SQL", "Database SQL category");
	        category3 = new Category(3L, "Spring Boot", "Spring Boot Framework category");
	}
	
	@Test
	void testGetAllCategories() {
		when(categoryRepository.save(any(Category.class))).thenReturn(category1);
		Category addCategory1 = categoryServiceImpl.createCategory(category1);
		
		when(categoryRepository.save(any(Category.class))).thenReturn(category2);
		Category addCategory2 = categoryServiceImpl.createCategory(category2);
		
		when(categoryRepository.save(any(Category.class))).thenReturn(category3);
		Category addCategory3 = categoryServiceImpl.createCategory(category3);
		
		ArrayList<Category> list = new ArrayList<Category>();
		list.add(addCategory1);
		list.add(addCategory2);
		list.add(addCategory3);
		
		when(categoryRepository.findAll()).thenReturn(list);
		List<Category> actual = categoryServiceImpl.getAllCategories();
		assertEquals(3, actual.size());
		assertNotEquals(4, actual.size());
		
	}
	
	@Test
	void testGetCategoryById() {
		when(categoryRepository.save(any(Category.class))).thenReturn(category1);
		Category addCategory = categoryServiceImpl.createCategory(category1);
		
		when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(addCategory));
		Category actual = categoryServiceImpl.getCategoryById(1L);
		assertEquals(1, actual.getId());
		
	}
	
	@Test
	void testCreateCategory() {
		when(categoryRepository.save(any(Category.class))).thenReturn(category1);
		Category actual = categoryServiceImpl.createCategory(category1);
		assertEquals(category1, actual);	
	}
	
	@Test
	void testUpdateCategory() {
		//save category
		when(categoryRepository.save(any(Category.class))).thenReturn(category1);
		Category createCategory = categoryServiceImpl.createCategory(category1);
		
	 	category1 = new Category(1L, "Core Java", "Updated Core Java category");
	 	when(categoryRepository.save(any(Category.class))).thenReturn(category1);
	 	Category updatedCategory = categoryServiceImpl.createCategory(category1);
	 	assertEquals("Updated Core Java category", updatedCategory.getDescription());
	 	assertEquals("Core Java", updatedCategory.getName());
	 	
		
	}
	
	@Test
	void testDeleteCategory() {
		
		when(categoryRepository.save(any(Category.class))).thenReturn(category1);
		Category addCategory = categoryServiceImpl.createCategory(category1);
		
		when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(addCategory));
		boolean deleteCategory = categoryServiceImpl.deleteCategory(1L);
		
		assertEquals(true, deleteCategory);
	}
	

}
