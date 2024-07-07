package com.org.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.org.dao.CategoryRepository;
import com.org.dao.SubcategoryRepository;
import com.org.entities.Category;
import com.org.entities.Subcategory;
import com.org.services.serviceImpl.SubcategoryServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SubcategoryServiceImplTest {

    @Mock
    private SubcategoryRepository subcategoryRepository;
    
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private SubcategoryServiceImpl subcategoryServiceImpl;

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
    	when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(subcategory1);
    	Subcategory addSubcategory1 = subcategoryServiceImpl.createSubcategory(subcategory1);
    	
    	when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(subcategory2);
    	Subcategory addSubcategory2 = subcategoryServiceImpl.createSubcategory(subcategory2);    	
    	List<Subcategory> list = new ArrayList<Subcategory>();
    	list.add(addSubcategory1);
    	list.add(addSubcategory2);

    	when(subcategoryRepository.findAll()).thenReturn(list);
    	List<Subcategory> allSubcategories = subcategoryServiceImpl.getAllSubcategories();
        Assertions.assertEquals(2, allSubcategories.size());
        Assertions.assertEquals("Spring MVC", allSubcategories.get(0).getName());
        Assertions.assertEquals("Hibernate", allSubcategories.get(1).getName());

    }
    
    @Test
    void testGetSubcategoryById() {
    	when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(subcategory1);
        Subcategory addSubcategory1 = subcategoryServiceImpl.createSubcategory(subcategory1);
        
        when(subcategoryRepository.findById(anyLong())).thenReturn(Optional.of(addSubcategory1));
        Subcategory result = subcategoryServiceImpl.getSubcategoryById(1l);

        Assertions.assertEquals("Spring MVC", result.getName());
        
    }
    
    @Test
    void testCreateSubcategory() {
    	when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(subcategory1);
    	Subcategory result = subcategoryServiceImpl.createSubcategory(subcategory1);

        Assertions.assertEquals("Spring MVC", result.getName());
        verify(subcategoryRepository, times(1)).save(subcategory1);
    }

    @Test
    void testUpdateSubcategory() {
    	when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(subcategory1);
    	Subcategory addSubcategory1 = subcategoryServiceImpl.createSubcategory(subcategory1);
    	
         Subcategory updated = new Subcategory(1L, category1, "Updated Spring MVC", "Spring MVC subcategory");
        
        when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(updated);
        Subcategory actual = subcategoryServiceImpl.updateSubcategory(updated);
        assertEquals(updated.getName(), actual.getName());
        verify(subcategoryRepository, times(1)).save(updated);
    }

    @Test
    void testDeleteSubcategory() {
        when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(subcategory1);
    	Subcategory addSubcategory1 = subcategoryServiceImpl.createSubcategory(subcategory1);
    	
    	when(subcategoryRepository.findById(anyLong())).thenReturn(Optional.of(addSubcategory1));
    	boolean result = subcategoryServiceImpl.deleteSubcategory(subcategory1.getId());
        Assertions.assertTrue(result);
    
    }

}
