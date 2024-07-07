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
    	
    	when(subcategoryService.createSubcategory(any(Subcategory.class))).thenReturn(subcategory1);
    	ResponseEntity<Object> addSubcategory1 = subcategoryController.createSubcategory(subcategory1);
    	
    	when(subcategoryService.createSubcategory(any(Subcategory.class))).thenReturn(subcategory2);
    	ResponseEntity<Object> addSubcategory2 = subcategoryController.createSubcategory(subcategory2);
    	
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
        
    	when(subcategoryService.createSubcategory(any(Subcategory.class))).thenReturn(subcategory1);
    	ResponseEntity<Object> addSubcategory = subcategoryController.createSubcategory(subcategory1);

        when(subcategoryService.getSubcategoryById(anyLong())).thenReturn(subcategory1);
        ResponseEntity<Subcategory> responseEntity = subcategoryController.getSubcategoryById(1l);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected status OK");
        assertEquals("Spring MVC", responseEntity.getBody().getName(), "Subcategory name should be Spring MVC");
    }

    @Test
    void testCreateSubcategory() {
    	when(subcategoryService.createSubcategory(any(Subcategory.class))).thenReturn(subcategory1);
    	ResponseEntity<Object> addSubcategory = subcategoryController.createSubcategory(subcategory1);
        assertEquals(HttpStatus.CREATED, addSubcategory.getStatusCode());

    }


    @Test
    void testUpdateSubcategory() {
    	
    	when(subcategoryService.createSubcategory(any(Subcategory.class))).thenReturn(subcategory1);
    	Subcategory addSubcategory = subcategoryService.createSubcategory(subcategory1);
    	
        Subcategory updatedSubcategory = new Subcategory(1L, category1, "Spring MVC Updated", "Updated Spring MVC subcategory");
        when(subcategoryService.updateSubcategory(updatedSubcategory)).thenReturn(updatedSubcategory);
        ResponseEntity<Subcategory> responseEntity = subcategoryController.updateSubcategory(1L, updatedSubcategory);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected status OK");
        assertEquals("Spring MVC Updated", responseEntity.getBody().getName(), "Subcategory name should be Spring MVC Updated");
    }

    @Test
    void testDeleteSubcategory() {
    	
    	when(subcategoryService.createSubcategory(any(Subcategory.class))).thenReturn(subcategory1);
    	ResponseEntity<Object> addSubcategory = subcategoryController.createSubcategory(subcategory1);

        when(subcategoryService.deleteSubcategory(anyLong())).thenReturn(true);
        ResponseEntity<HttpStatus> responseEntity = subcategoryController.deleteSubcategory(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected status OK");
    }

}
