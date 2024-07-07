package com.org.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.InputStream;
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
import org.springframework.mock.web.MockMultipartFile;

import com.org.entities.Category;
import com.org.entities.MCQQuestion;
import com.org.entities.Subcategory;
import com.org.services.MCQQuestionService;
import com.org.services.SubcategoryService;


@ExtendWith(MockitoExtension.class)
public class MCQQuestionControllerTest {

	
    @Mock
    private MCQQuestionService questionService;
    
    @Mock
    private SubcategoryService subcategoryService;

    @InjectMocks
    private MCQQuestionController mcqController;
    
    
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
    	
    	when(questionService.createQuestion(any(MCQQuestion.class))).thenReturn(mockQuestion1);
    	ResponseEntity<MCQQuestion> addQuesion1 = mcqController.createQuestion(mockQuestion1);
    	
    	when(questionService.createQuestion(any(MCQQuestion.class))).thenReturn(mockQuestion2);
    	ResponseEntity<MCQQuestion> addQuesion2 = mcqController.createQuestion(mockQuestion2);
        
	List<MCQQuestion> list=new ArrayList<MCQQuestion>();
        list.add(mockQuestion1);
        list.add(mockQuestion2);

        when(questionService.getAllQuestions()).thenReturn(list);
        ResponseEntity<List<MCQQuestion>> allQuestions = mcqController.getAllQuestions();
        assertEquals(HttpStatus.OK, allQuestions.getStatusCode());
    }

    
    @Test
    void testGetQuestionById() {
    	
    	//Adding Mock Data
    	when(questionService.createQuestion(any(MCQQuestion.class))).thenReturn(mockQuestion1);
        ResponseEntity<MCQQuestion> addQuestion1 = mcqController.createQuestion(mockQuestion1);

        when(questionService.getQuestionById(anyLong())).thenReturn(mockQuestion1);
        ResponseEntity<MCQQuestion> actual = mcqController.getQuestionById(1L);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(1L, actual.getBody().getId());
    }

  
    @Test
    void testCreateQuestion() {
    	when(questionService.createQuestion(any(MCQQuestion.class))).thenReturn(mockQuestion1);
	ResponseEntity<MCQQuestion> actual = mcqController.createQuestion(mockQuestion1);

        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(1L, actual.getBody().getId());
    }

 
    @Test
    void testUpdateQuestion() {
	 	
    	when(questionService.createQuestion(any(MCQQuestion.class))).thenReturn(mockQuestion1);
    	ResponseEntity<MCQQuestion> addQuestion = mcqController.createQuestion(mockQuestion1);
    	
    	//Updated Data
        mockQuestion1 = new MCQQuestion(1L,subcategory1, "SpringBoot Updated", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);

        when(questionService.updateQuestion(any(MCQQuestion.class))).thenReturn(mockQuestion1);
        ResponseEntity<MCQQuestion> updateQuestion = mcqController.updateQuestion(1L, mockQuestion1);
        
        // Assertions
        assertEquals(HttpStatus.OK, updateQuestion.getStatusCode());
        assertEquals(1L, updateQuestion.getBody().getId());
    }

    @Test
    void testDeleteQuestion() {
   
    	when(questionService.createQuestion(any(MCQQuestion.class))).thenReturn(mockQuestion1);
    	ResponseEntity<MCQQuestion> addQuestion = mcqController.createQuestion(mockQuestion1);
    	
        when(questionService.deleteQuestion(anyLong())).thenReturn(true);
        ResponseEntity<HttpStatus> responseEntity1 = mcqController.deleteQuestion(1L);
        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());
        
    }
    
    @Test
    public void testUploadBulkQuestion() throws Exception {
        InputStream inputStream = new FileInputStream("D:\\QuestionBank.xlsx");
        MockMultipartFile file = new MockMultipartFile("file", "QuestionBank.xlsx", "text/xlsx", inputStream);
        List<MCQQuestion> list = new ArrayList<>();
        
        when(questionService.uploadQuestions(file)).thenReturn(list);
       ResponseEntity<Object> uploadFile = mcqController.uploadFile(file);
        assertEquals(HttpStatus.CREATED, uploadFile.getStatusCode());
    }
   
}
