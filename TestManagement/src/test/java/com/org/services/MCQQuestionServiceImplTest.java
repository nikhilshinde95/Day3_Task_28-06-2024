package com.org.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import org.springframework.boot.test.context.SpringBootTest;

import com.org.dao.MCQQuestionRepository;
import com.org.dao.SubcategoryRepository;
import com.org.entities.Category;
import com.org.entities.MCQQuestion;
import com.org.entities.Subcategory;
import com.org.services.serviceImpl.MCQQuestionServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MCQQuestionServiceImplTest {

    @Mock
    private MCQQuestionRepository mcqDao;
    
    @Mock
    private SubcategoryRepository subcategoryRepository;
    
    @InjectMocks
    private MCQQuestionServiceImpl questionServiceImpl;
    
    
    private Category category1;
    private Subcategory subcategory1;
    private Subcategory subcategory2;
    private MCQQuestion mockQuestion1;
    private MCQQuestion mockQuestion2;

    @BeforeEach
    public void setUp() {
    	
    	 category1 = new Category(1L, "Java", "Core Java category");
         subcategory1 = new Subcategory(1L, category1, "Spring MVC", "Spring MVC subcategory");
         subcategory2 = new Subcategory(2L, category1, "Hibernate", "Hibernate subcategory");
         mockQuestion1 = new MCQQuestion(1L,subcategory1, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);
         mockQuestion2 = new MCQQuestion(2L,subcategory2, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);
     }


    @Test
    void testGetAllQuestions() {
    	
    	when(mcqDao.save(any(MCQQuestion.class))).thenReturn(mockQuestion1);
    	when(subcategoryRepository.findById(anyLong())).thenReturn(Optional.of(subcategory1));
    	MCQQuestion addQuestion1 = questionServiceImpl.createQuestion(mockQuestion1);
    	
    	when(mcqDao.save(any(MCQQuestion.class))).thenReturn(mockQuestion2);
    	when(subcategoryRepository.findById(anyLong())).thenReturn(Optional.of(subcategory2));
    	MCQQuestion addQuestion2 = questionServiceImpl.createQuestion(mockQuestion2);

    	List<MCQQuestion> list=new ArrayList<MCQQuestion>();
    	list.add(addQuestion1);
    	list.add(addQuestion2);
    	
    	when(mcqDao.findAll()).thenReturn(list);
    	List<MCQQuestion> result = questionServiceImpl.getAllQuestions();
        assertEquals(2, result.size());
        assertEquals(mockQuestion1.getId(), result.get(0).getId());
        assertEquals(mockQuestion2.getId(), result.get(1).getId());
    }

    @Test
    void testGetQuestionById() {
       
    	when(mcqDao.save(any(MCQQuestion.class))).thenReturn(mockQuestion1);
    	when(subcategoryRepository.findById(anyLong())).thenReturn(Optional.of(subcategory1));
    	MCQQuestion addQuestion1 = questionServiceImpl.createQuestion(mockQuestion1);
	 
    	when(mcqDao.findById(anyLong())).thenReturn(Optional.of(addQuestion1));
    	MCQQuestion result = questionServiceImpl.getQuestionById(1L);
        assertNotNull(result);
        assertEquals(mockQuestion1.getId(), result.getId());
        assertEquals(mockQuestion1.getQuestion(), result.getQuestion());
    }

    @Test
    void testCreateQuestion() {
       
    	when(mcqDao.save(any(MCQQuestion.class))).thenReturn(mockQuestion1);
    	when(subcategoryRepository.findById(anyLong())).thenReturn(Optional.of(subcategory1));
	MCQQuestion addQuestion1 = questionServiceImpl.createQuestion(mockQuestion1);
        assertNotNull(addQuestion1);
        assertEquals(mockQuestion1.getId(), addQuestion1.getId());
        assertEquals(mockQuestion1.getQuestion(), addQuestion1.getQuestion());
    }

    @Test
    void testUpdateQuestion() {
      
    	when(mcqDao.save(any(MCQQuestion.class))).thenReturn(mockQuestion1);
    	when(subcategoryRepository.findById(anyLong())).thenReturn(Optional.of(subcategory1));
       	MCQQuestion addQuestion1 = questionServiceImpl.createQuestion(mockQuestion1);
       
        MCQQuestion updatedQuestion = new MCQQuestion(addQuestion1.getId(),subcategory1, "SpringBoot Updated", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);
        when(mcqDao.findById(anyLong())).thenReturn(Optional.of(updatedQuestion));
        MCQQuestion result = questionServiceImpl.updateQuestion(updatedQuestion);
        assertNotNull(result);
        assertEquals(updatedQuestion.getId(), result.getId());
        assertEquals(addQuestion1.getQuestion(), result.getQuestion());
    }

    @Test
    void testDeleteQuestion() {
    	when(mcqDao.save(any(MCQQuestion.class))).thenReturn(mockQuestion1);
    	when(subcategoryRepository.findById(anyLong())).thenReturn(Optional.of(subcategory1));
	MCQQuestion addQuestion1 = questionServiceImpl.createQuestion(mockQuestion1);
	 	
	when(mcqDao.findById(anyLong())).thenReturn(Optional.of(addQuestion1));
        boolean result = questionServiceImpl.deleteQuestion(1L);
        assertTrue(result);
    }
}
