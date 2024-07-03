package com.org.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.org.controller.CategoryController;
import com.org.entities.Category;
import com.org.services.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private Category category1;
    private Category category2;
    private Category category3;

    @BeforeEach
    public void setUp() {
       
        category1 = new Category(1L, "Java", "Core Java category");
        category2 = new Category(2L, "SQL", "Database SQL category");
        category3 = new Category(3L, "Spring Boot", "Spring Boot Framework category");
    }

    @Test
    void testGetAllCategories() {
 
        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(category1);
        mockCategories.add(category2);
        mockCategories.add(category3);

        when(categoryService.getAllCategories()).thenReturn(mockCategories);

        List<Category> actual = categoryController.getAllCategories();

        // Assertions
        assertEquals(3, actual.size(), "Expected 3 categories");
        assertEquals("Java", actual.get(0).getName(), "First category name should be Java");
        assertEquals("SQL", actual.get(1).getName(), "Second category name should be SQL");
        assertEquals("Spring Boot", actual.get(2).getName(), "Third category name should be Spring Boot");
    }

    @Test
    void testGetCategoryById() {
    	
       
    	when(categoryService.getCategoryById(anyLong())).thenReturn(category1);
    	ResponseEntity<Category> actual = categoryController.getCategoryById(1L);
        
        assertEquals(1L, actual.getBody().getId());   
        
    }


    @Test
    void testCreateCategory() {
    	
        Category newCategory = new Category(null, "Java", "Core Java category");
        Category savedCategory = new Category(1L, "Java", "Core Java category");

        when(categoryService.createCategory(newCategory)).thenReturn(savedCategory);
        ResponseEntity<Object> responseEntity = categoryController.createCategory(newCategory);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode(), "Expected status Created");
    
    }

    @Test
    void testUpdateCategory() {
        Long categoryId = 1L;
        Category updatedCategory = new Category(categoryId, "Java", "Updated Core Java category");

        when(categoryService.updateCategory(updatedCategory)).thenReturn(updatedCategory);

        ResponseEntity<Category> responseEntity = categoryController.updateCategory(categoryId, updatedCategory);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected status OK");
        assertEquals("Updated Core Java category", responseEntity.getBody().getDescription(), "Category description should be updated");
    }

    @Test
    void testDeleteCategory() {
        Long categoryId = 1L;
        when(categoryService.deleteCategory(categoryId)).thenReturn(true);

        ResponseEntity<HttpStatus> responseEntity = categoryController.deleteCategory(categoryId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected status OK");
    }
}
