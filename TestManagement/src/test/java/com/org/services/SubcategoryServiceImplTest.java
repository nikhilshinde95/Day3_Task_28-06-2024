package com.org.services;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.org.dao.SubcategoryRepository;
import com.org.entities.Category;
import com.org.entities.Subcategory;

@SpringBootTest
public class SubcategoryServiceImplTest {

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @InjectMocks
    private SubcategoryServiceImpl subcategoryService;

    private Category category1;
    private Subcategory subcategory1;
    private Subcategory subcategory2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        category1 = new Category(1L, "Java", "Core Java category");
        subcategory1 = new Subcategory(1L, category1, "Spring MVC", "Spring MVC subcategory");
        subcategory2 = new Subcategory(2L, category1, "Hibernate", "Hibernate subcategory");
    }

    @Test
    void getAllSubcategories() {
        // Mocking behavior of repository
        when(subcategoryRepository.findAll()).thenReturn(Arrays.asList(subcategory1, subcategory2));

        // Call the service method
        List<Subcategory> result = subcategoryService.getAllSubcategories();

        // Assert the result
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Spring MVC", result.get(0).getName());
        Assertions.assertEquals("Hibernate", result.get(1).getName());

    }
    
    @Test
    void getSubcategoryById() {
        Long id = 1L;
        when(subcategoryRepository.findById(id)).thenReturn(Optional.of(subcategory1));

        Subcategory result = subcategoryService.getSubcategoryById(id);

        Assertions.assertEquals("Spring MVC", result.getName());
        verify(subcategoryRepository, times(1)).findById(id);
    }
    
    @Test
    void createSubcategory() {
        when(subcategoryRepository.save(subcategory1)).thenReturn(subcategory1);

        Subcategory result = subcategoryService.createSubcategory(subcategory1);

        Assertions.assertEquals("Spring MVC", result.getName());
        verify(subcategoryRepository, times(1)).save(subcategory1);
    }

    @Test
    void updateSubcategory() {
        when(subcategoryRepository.save(subcategory1)).thenReturn(subcategory1);

        Subcategory result = subcategoryService.updateSubcategory(subcategory1);

        Assertions.assertEquals("Spring MVC", result.getName());
        verify(subcategoryRepository, times(1)).save(subcategory1);
    }

    @Test
    void deleteSubcategory() {
        Long id = 1L;
        when(subcategoryRepository.findById(id)).thenReturn(Optional.of(subcategory1));

        boolean result = subcategoryService.deleteSubcategory(id);

        Assertions.assertTrue(result);
        verify(subcategoryRepository, times(1)).delete(subcategory1);
    
    }


}
