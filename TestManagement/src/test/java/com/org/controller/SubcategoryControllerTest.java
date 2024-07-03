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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.org.controller.SubcategoryController;
import com.org.entities.Category;
import com.org.entities.Subcategory;
import com.org.services.CategoryService;
import com.org.services.SubcategoryService;

@ExtendWith(MockitoExtension.class)
public class SubcategoryControllerTest {

    @Mock
    private SubcategoryService subcategoryService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private SubcategoryController subcategoryController;

    private Category category1;
    private Subcategory subcategory1;
    private Subcategory subcategory2;

    @BeforeEach
    public void setUp() {
    	
        category1 = new Category(1L, "Java", "Core Java category");
        subcategory1 = new Subcategory(1L, category1, "Spring MVC", "Spring MVC subcategory");
        subcategory2 = new Subcategory(2L, category1, "Hibernate", "Hibernate subcategory");
    }

    @Test
    void testGetAllSubcategories() {
    	
        List<Subcategory> list = new ArrayList<>();
        list.add(subcategory1);
        list.add(subcategory2);
 
        when(subcategoryService.getAllSubcategories()).thenReturn(list);
        List<Subcategory> returnedSubcategories = subcategoryController.getAllSubcategories();
        
        assertEquals(2, returnedSubcategories.size(), "Expected 2 subcategories");
        assertEquals("Spring MVC", returnedSubcategories.get(0).getName(), "First subcategory name should be Spring MVC");
        assertEquals("Hibernate", returnedSubcategories.get(1).getName(), "Second subcategory name should be Hibernate");
    }

    @Test
    void testGetSubcategoryByIdValid() {
        Long subcategoryId = 1L;

        when(subcategoryService.getSubcategoryById(subcategoryId)).thenReturn(subcategory1);

        ResponseEntity<Subcategory> responseEntity = subcategoryController.getSubcategoryById(subcategoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected status OK");
        assertEquals("Spring MVC", responseEntity.getBody().getName(), "Subcategory name should be Spring MVC");
    }

    @Test
    void testGetSubcategoryByIdNotFound() {
        Long subcategoryId = 99L;

        when(subcategoryService.getSubcategoryById(subcategoryId)).thenReturn(null);

        ResponseEntity<Subcategory> responseEntity = subcategoryController.getSubcategoryById(subcategoryId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode(), "Expected status Not Found");
    }

    @Test
    void testCreateSubcategory() {
        Subcategory newSubcategory = new Subcategory(null, category1, "Spring Boot", "Spring Boot subcategory");
        Subcategory savedSubcategory = new Subcategory(3L, category1, "Spring Boot", "Spring Boot subcategory");
        
        when(categoryService.getCategoryById(category1.getId())).thenReturn(category1);
        when(subcategoryService.createSubcategory(newSubcategory)).thenReturn(savedSubcategory);

        ResponseEntity<Object> responseEntity = subcategoryController.createSubcategory(newSubcategory);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    @Test
    void testCreateSubcategoryCategoryNotFound() {
        Subcategory newSubcategory = new Subcategory(null, category1, "Spring Boot", "Spring Boot subcategory");
        
        when(categoryService.getCategoryById(category1.getId())).thenReturn(null);
        ResponseEntity<Object> responseEntity = subcategoryController.createSubcategory(newSubcategory);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateSubcategory() {
    	Long subcategoryId=1L;
        Subcategory updatedSubcategory = new Subcategory(subcategoryId, category1, "Spring MVC Updated", "Updated Spring MVC subcategory");
        when(categoryService.getCategoryById(category1.getId())).thenReturn(category1);
        when(subcategoryService.updateSubcategory(updatedSubcategory)).thenReturn(updatedSubcategory);

        ResponseEntity<Subcategory> responseEntity = subcategoryController.updateSubcategory(subcategoryId, updatedSubcategory);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected status OK");
        assertEquals("Spring MVC Updated", responseEntity.getBody().getName(), "Subcategory name should be Spring MVC Updated");
    }

    @Test
    void testUpdateSubcategoryCategoryNotFound() {
        Long subcategoryId = 1L;
        Subcategory updatedSubcategory = new Subcategory(subcategoryId, category1, "Spring MVC Updated", "Updated Spring MVC subcategory");

        when(categoryService.getCategoryById(category1.getId())).thenReturn(null);

        ResponseEntity<Subcategory> responseEntity = subcategoryController.updateSubcategory(subcategoryId, updatedSubcategory);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode(), "Expected status Not Found");
    }

    @Test
    void testDeleteSubcategory() {
        Long subcategoryId = 1L;

        when(subcategoryService.deleteSubcategory(subcategoryId)).thenReturn(true);
        ResponseEntity<HttpStatus> responseEntity = subcategoryController.deleteSubcategory(subcategoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected status OK");
    }

    @Test
    void testDeleteSubcategoryNotFound() {
        Long subcategoryId = 99L;
        when(subcategoryService.deleteSubcategory(subcategoryId)).thenReturn(false);
        ResponseEntity<HttpStatus> responseEntity = subcategoryController.deleteSubcategory(subcategoryId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode(), "Expected status Not Found");
    }
}
