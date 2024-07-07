package com.org.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.org.dao.SubcategoryRepository;
import com.org.entities.Category;
import com.org.entities.Subcategory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
public class SubcategoryRepositoryTest {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    private Subcategory subcategory1;
    private Subcategory subcategory2;
    
    private Subcategory dbSubcategory1;
    private Subcategory dbSubcategory2;

    @BeforeEach
    public void setUp() {
        Category category = new Category(1L, "Programming", "Programming category");
        
        subcategory1 = new Subcategory(1L, category, "Spring Boot", "Spring Boot subcategory");
        subcategory2 = new Subcategory(2L, category, "Hibernate", "Hibernate subcategory");
        
        dbSubcategory1 = subcategoryRepository.save(subcategory1);
        dbSubcategory2 = subcategoryRepository.save(subcategory2);
    }
    
    @AfterEach
    public void tearDown() {
        subcategoryRepository.delete(subcategory1);
        subcategoryRepository.delete(subcategory2);
    }
    
    @Test
    public void testFindAll() {
    	List<Subcategory> list = subcategoryRepository.findAll();
    	
    	assertNotNull(list);
    	assertNotEquals(0L, list.size());
    	
    }
    
    @Test
    public void testFindById() {
    	Optional<Subcategory> result = subcategoryRepository.findById(dbSubcategory1.getId());
    	
    	assertNotNull(result);
    	assertEquals("Spring Boot", result.get().getName());
    	assertEquals("Spring Boot subcategory", result.get().getDescription());
    }
    
    @Test
    public void testSave() {
    	Subcategory saved = subcategoryRepository.save(subcategory1);
    	assertNotNull(saved);
    	assertEquals(1, saved.getId());
    	assertNotEquals(2, saved.getId());
    	assertEquals("Spring Boot", saved.getName());
    }
    
    @Test
    public void deleteByIdSubcategory() {
    	subcategoryRepository.deleteById(1L);
    
    }
    
    @Test
    public void testFindByName() {
    	List<Subcategory> result = subcategoryRepository.findByName(dbSubcategory1.getName());
    	assertEquals("Spring Boot", result.getFirst().getName());
    	assertNotNull(result);
    	assertNotEquals(4, result.size());
    }
    
    @Test
    public void testFindByNameandDescription() {
    	Optional<Subcategory> result = subcategoryRepository.findByNameandDescription(dbSubcategory1.getName(), dbSubcategory1.getDescription());
    	assertEquals("Spring Boot", result.get().getName());
    	assertEquals("Spring Boot subcategory", result.get().getDescription());
    	assertNotNull(result);
    }
    
    
    @Test
    public void testDelete() {
    	subcategoryRepository.deleteById(dbSubcategory1.getId());
    	Optional<Subcategory> checkDelete = subcategoryRepository.findById(dbSubcategory1.getId());
    	assertEquals(true, checkDelete.isEmpty());
    }
    

   
}
