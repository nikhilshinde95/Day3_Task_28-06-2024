package com.org.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.org.entities.Category;
import com.org.entities.MCQQuestion;
import com.org.entities.Subcategory;
import com.org.services.MCQQuestionServiceImpl;
import com.org.services.MCQService;
import com.org.services.SubcategoryService;

@ExtendWith(MockitoExtension.class)
public class MCQQuestionControllerTest {

    @Mock
    private MCQService questionService;
    
    @Mock
    private SubcategoryService subcategoryService;

    @InjectMocks
    private MCQQuestionController controller;
    
    
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
    void getAllQuestions() {
        
	 	MCQQuestion mockQuestion1 = new MCQQuestion(1L,subcategory1, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);
	 	MCQQuestion mockQuestion2 = new MCQQuestion(2L,subcategory2, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);
        List<MCQQuestion> expected = new ArrayList<>();
        expected.add(mockQuestion1);
        expected.add(mockQuestion2);

        when(questionService.getAllQuestions()).thenReturn(expected);

        List<MCQQuestion> result = controller.getAllQuestions();

        // Assertions
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    
    @Test
    void getQuestionById() {
    	
        // Mock data
	 	MCQQuestion mockQuestion1 = new MCQQuestion(1L,subcategory1, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);

        // Mock behavior
        when(questionService.getQuestionById(anyLong())).thenReturn(mockQuestion1);

        // Call controller method
        ResponseEntity<MCQQuestion> actual = controller.getQuestionById(1L);

        // Assertions
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(1L, actual.getBody().getId());
    }

  
    @Test
    void createQuestion() {
       
	 	MCQQuestion mockQuestion1 = new MCQQuestion(1L,subcategory1, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);
        ResponseEntity<MCQQuestion> mockResponseEntity = ResponseEntity.status(HttpStatus.CREATED).body(mockQuestion1);

        when(questionService.createQuestion(any(MCQQuestion.class))).thenReturn(mockQuestion1);

        ResponseEntity<MCQQuestion> actual = controller.createQuestion(mockQuestion1);

        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(1L, actual.getBody().getId());
    }

 
    @Test
    void updateQuestion() {
	 	MCQQuestion mockQuestion1 = new MCQQuestion(1L,subcategory1, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);
        ResponseEntity<MCQQuestion> mockResponseEntity = ResponseEntity.ok().body(mockQuestion1);

        when(questionService.updateQuestion(any(MCQQuestion.class))).thenReturn(mockQuestion1);

        ResponseEntity<MCQQuestion> actual = controller.updateQuestion(1L, mockQuestion1);

        // Assertions
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(1L, actual.getBody().getId());
    }

    @Test
    void deleteQuestion() {
   
	 	MCQQuestion mockQuestion1 = new MCQQuestion(1L,subcategory1, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);

        when(questionService.deleteQuestion(anyLong())).thenReturn(true);

        ResponseEntity<HttpStatus> responseEntity1 = controller.deleteQuestion(1L);

        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());
        
    }
    
    
}
