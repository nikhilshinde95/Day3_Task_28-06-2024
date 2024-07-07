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

import com.org.dao.MCQQuestionRepository;
import com.org.entities.Category;
import com.org.entities.MCQQuestion;
import com.org.entities.Subcategory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
public class MCQQuestionRepositoryTest {

	@Autowired
	private MCQQuestionRepository mcqQuestionRepository ;

    private Category category1;
    private Subcategory subcategory1;
    private Subcategory subcategory2;
    
    private MCQQuestion mockQuestion1;
    private MCQQuestion mockQuestion2;
    
   private MCQQuestion dbMockQuestion1; 
   private MCQQuestion dbMockQuestion2; 

    @BeforeEach
    public void setUp() {
        category1 = new Category(1L, "Java", "Core Java category");
        subcategory1 = new Subcategory(1L, category1, "Spring MVC", "Spring MVC subcategory");
        subcategory2 = new Subcategory(2L, category1, "Hibernate", "Hibernate subcategory");
        mockQuestion1 = new MCQQuestion(1L,subcategory1, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);
        mockQuestion2 = new MCQQuestion(2L,subcategory2, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);
        
        dbMockQuestion1 = mcqQuestionRepository.save(mockQuestion1);
        dbMockQuestion2 = mcqQuestionRepository.save(mockQuestion2);
    }
    
    
    @AfterEach
    public void tearDown() {
      mcqQuestionRepository.delete(mockQuestion1);
      mcqQuestionRepository.delete(mockQuestion1);
    }
    
    
    @Test
    public void testFindAll() {
    	List<MCQQuestion> list = mcqQuestionRepository.findAll();
    	
    	assertNotNull(list);
    	assertNotEquals(0L, list.size());
    }
    
    @Test
    public void testFindById() {
    	Optional<MCQQuestion> actual = mcqQuestionRepository.findById(dbMockQuestion1.getId());  	
    	 assertNotNull(actual);
    	 assertEquals(dbMockQuestion1.getQuestion(),actual.get().getQuestion());
    	 assertEquals(dbMockQuestion1.getOptionOne(),actual.get().getOptionOne());
    	
    }
    
    @Test
     public void testSave() {
    	 MCQQuestion saved = mcqQuestionRepository.save(mockQuestion1);
     	assertNotNull(saved);
     	assertNotEquals("SpringBoot Application",saved.getQuestion());
     	assertEquals("SpringBoot", saved.getQuestion());
     }
    
    @Test
    public void testUpdateQuestions() {
        mockQuestion1.setQuestion("Spring Boot Application");
        MCQQuestion updatedQuestion = mcqQuestionRepository.save(mockQuestion1);
       Optional<MCQQuestion> updated = mcqQuestionRepository.findById(updatedQuestion.getId());
       assertEquals(updatedQuestion, updated.get());
       assertEquals("Spring Boot Application", updated.get().getQuestion());
    }
    
    @Test
    public void testDelete() {
    	mcqQuestionRepository.deleteById(dbMockQuestion1.getId());
    	
    	Optional<MCQQuestion> checkDelete = mcqQuestionRepository.findById(dbMockQuestion1.getId());
    	assertEquals(true,checkDelete.isEmpty());
    }
    
    
    
        
    
    

  
}
