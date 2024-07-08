package com.org.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.org.entities.Category;
import com.org.services.CategoryService;

@SpringBootTest
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
    	
    	when(categoryService.createCategory(any(Category.class))).thenReturn(category1);
    	ResponseEntity<Object> addCategory1 = categoryController.createCategory(category1);
    	
    	when(categoryService.createCategory(any(Category.class))).thenReturn(category2);
    	ResponseEntity<Object> addCategory2 = categoryController.createCategory(category2);
    	
    	when(categoryService.createCategory(any(Category.class))).thenReturn(category3);
    	ResponseEntity<Object> addCategory3 = categoryController.createCategory(category3);
    	
        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(category1);
        mockCategories.add(category2);
        mockCategories.add(category3);

        //Mock behavior
        when(categoryService.getAllCategories()).thenReturn(mockCategories);
        ResponseEntity<List<Category>> actual = categoryController.getAllCategories();
        assertEquals(HttpStatus.OK,actual.getStatusCode());
        assertNotNull(actual);
        assertEquals(3, actual.getBody().size());
    }

    @Test
    void testGetCategoryById() {
    	when(categoryService.createCategory(any(Category.class))).thenReturn(category1);
    	ResponseEntity<Object> addcategory = categoryController.createCategory(category1);

    	when(categoryService.getCategoryById(anyLong())).thenReturn(category1);
    	ResponseEntity<Category> actual = categoryController.getCategoryById(1L); 
        assertEquals(1L, actual.getBody().getId());   
    }

    @Test
    void testCreateCategory() {
    	
    	when(categoryService.createCategory(any(Category.class))).thenReturn(category1);
    	ResponseEntity<Object> addCategory = categoryController.createCategory(category1);	
        assertEquals(HttpStatus.CREATED, addCategory.getStatusCode());  
    }

    @Test
    void testUpdateCategory() {
    	
        //Creating Category
    	when(categoryService.createCategory(any(Category.class))).thenReturn(category1);
    	ResponseEntity<Object> addCategory = categoryController.createCategory(category1);
    	
    	//Updated Category
        Category updatedCategory = new Category(1L, "Java", "Updated Core Java category");
        
        when(categoryService.updateCategory(any(Category.class))).thenReturn(updatedCategory);
        ResponseEntity<Category> responseEntity = categoryController.updateCategory(category1.getId(), updatedCategory);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected status OK");
        assertEquals("Updated Core Java category", responseEntity.getBody().getDescription());
    }

    @Test
    void testDeleteCategory() {
    	when(categoryService.createCategory(any(Category.class))).thenReturn(category1);
    	ResponseEntity<Object> addCategory = categoryController.createCategory(category1);
    	
        when(categoryService.deleteCategory(anyLong())).thenReturn(true);
        ResponseEntity<HttpStatus> responseEntity = categoryController.deleteCategory(1l);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected status OK");
    }
}
