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

import com.org.dao.CategoryRepository;
import com.org.entities.Category;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
public class CategoryRepositoryTest {
   	
	@Autowired
	private CategoryRepository categoryRepository;
	
    private Category category1;
    private Category category2;
    private Category category3;
    
    private Category dbCategory1;
    private Category dbCategory2;
    private Category dbCategory3;
    
    @BeforeEach
    public void setUp() { 
        category1 = new Category(1L, "Java", "Core Java category");
        category2 = new Category(2L, "SQL", "Database SQL category");
        category3 = new Category(3L, "Spring Boot", "Spring Boot Framework category");
        dbCategory1 = categoryRepository.save(category1);
        dbCategory2 = categoryRepository.save(category2);
        dbCategory3 = categoryRepository.save(category3);
    }
    
    @AfterEach
    public void tearDown() {
        categoryRepository.delete(category1);
        categoryRepository.delete(category2);
        categoryRepository.delete(category3);
    }
    
    @Test
    public void testFindAll() {
    	List<Category> result = categoryRepository.findAll();
    	assertEquals(3, result.size());
    	assertNotEquals(0L, result.size());	
    }
    
    @Test
    public void testFindById() {
    	Optional<Category> actual = categoryRepository.findById(category1.getId());
    	 assertNotNull(actual);
    	 assertEquals(category1.getName(), actual.get().getName());
    	 assertEquals(category1.getDescription(), actual.get().getDescription());
    }
    
    
    @Test
    public void testSave() {
    	Category saved = categoryRepository.save(category1);
    	assertEquals(1, saved.getId());
    	assertNotNull(saved);
    	assertNotEquals("Python",saved.getName());
    }
    
    @Test
    public void testUpdate() {
       
    	category1 = new Category(1L, "Python", "Core Python category");
        categoryRepository.save(category1);
        Optional<Category> updatedCategory = categoryRepository.findById(dbCategory1.getId());

        assertNotNull(updatedCategory);
        assertEquals("Python", updatedCategory.get().getName());
        assertEquals("Core Python category", updatedCategory.get().getDescription());
    }
    
    
    @Test
    public void testFindByName() {
    	   List<Category> list = categoryRepository.findByName("Java");

    	    assertNotNull(list);
    	    assertEquals("Java", list.getFirst().getName());
    	    assertNotEquals(4, list.size());
    }
    
    @Test
    public void testFindByNameandDescription() {
    	Optional<Category> categoryOpt = categoryRepository.findByNameandDescription(dbCategory1.getName(), dbCategory1.getDescription());
    	assertEquals("Java", categoryOpt.get().getName());
    	assertEquals("Core Java category", categoryOpt.get().getDescription());
    }
    
    @Test
    public void testDelete() {
    	categoryRepository.deleteById(category1.getId());
    	Optional<Category> checkDelete = categoryRepository.findById(dbCategory1.getId());
    	assertEquals(true, checkDelete.isEmpty());
    }
 

 
}
