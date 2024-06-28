package com.org.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import com.org.dao.MCQDao;
import com.org.entities.MCQQuestion;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MCQQuestionServiceImplTest {

    @Mock
    private MCQDao mcqDao;

    @InjectMocks
    private MCQQuestionServiceImpl questionService;

    @Test
    void testGetAllQuestions() {
        
	 	MCQQuestion mockQuestion1 = new MCQQuestion(1L,null, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);
	 	MCQQuestion mockQuestion2 = new MCQQuestion(1L,null, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);
        List<MCQQuestion> mockQuestions = new ArrayList<>();
        mockQuestions.add(mockQuestion1);
        mockQuestions.add(mockQuestion2);


        when(mcqDao.findAll()).thenReturn(mockQuestions);
        List<MCQQuestion> result = questionService.getAllQuestions();

        // Assertions
        assertEquals(2, result.size());
        assertEquals(mockQuestion1.getId(), result.get(0).getId());
        assertEquals(mockQuestion2.getId(), result.get(1).getId());
    }

    @Test
    void testGetQuestionById() {
       
	 	MCQQuestion mockQuestion1 = new MCQQuestion(1L,null, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);

        when(mcqDao.findById(anyLong())).thenReturn(Optional.of(mockQuestion1));
        MCQQuestion result = questionService.getQuestionById(1L);

        // Assertions
        assertNotNull(result);
        assertEquals(mockQuestion1.getId(), result.getId());
        assertEquals(mockQuestion1.getQuestion(), result.getQuestion());
    }

    @Test
    void testCreateQuestion() {
       
	 	MCQQuestion mockQuestion1 = new MCQQuestion(1L,null, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);

        when(mcqDao.save(any(MCQQuestion.class))).thenReturn(mockQuestion1);

        MCQQuestion result = questionService.createQuestion(mockQuestion1);

        // Assertions
        assertNotNull(result);
        assertEquals(mockQuestion1.getId(), result.getId());
        assertEquals(mockQuestion1.getQuestion(), result.getQuestion());
    }

    @Test
    void testUpdateQuestion() {
      
        MCQQuestion mockQuestion = new MCQQuestion(1L,null, "Sample Question", "Option A", "Option B", "Option C", "Option D", "Option A", 4, -1);

        when(mcqDao.save(any(MCQQuestion.class))).thenReturn(mockQuestion);
        MCQQuestion result = questionService.updateQuestion(mockQuestion);

        // Assertions
        assertNotNull(result);
        assertEquals(mockQuestion.getId(), result.getId());
        assertEquals(mockQuestion.getQuestion(), result.getQuestion());
    }

    @Test
    void testDeleteQuestion() {
       
	 	MCQQuestion mockQuestion1 = new MCQQuestion(1L,null, "SpringBoot", "In Spring Boot @RestController annotation is equivalent to", "@Controller and @PostMapping", "@Controller and @Component", "@Controller and @ResponseBody", "@Controller and @ResponseStatus", 3, -1);

        when(mcqDao.findById(anyLong())).thenReturn(Optional.of(mockQuestion1));
        doNothing().when(mcqDao).delete(any(MCQQuestion.class));

        boolean result = questionService.deleteQuestion(1L);

        // Assertions
        assertTrue(result);
    }
}
