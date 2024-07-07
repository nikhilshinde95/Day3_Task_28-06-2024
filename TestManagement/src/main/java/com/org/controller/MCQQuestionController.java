package com.org.controller;

import com.org.entities.MCQQuestion;
import com.org.services.MCQQuestionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class MCQQuestionController {

    private static final Logger logger = LoggerFactory.getLogger(MCQQuestionController.class);

    @Autowired
    private MCQQuestionService questionService;

    @GetMapping()
    public ResponseEntity< List<MCQQuestion>> getAllQuestions() {
        logger.info("Fetching all questions");
        List<MCQQuestion> allQuestion = questionService.getAllQuestions();
        logger.info("List of MCQ Question is fetched successfully..");
        return ResponseEntity.status(HttpStatus.OK).body(allQuestion);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<MCQQuestion> getQuestionById(@PathVariable("questionId") long questionId) {
   
            logger.info("Fetching question with id: {}", questionId);
            MCQQuestion question = questionService.getQuestionById(questionId);
            logger.info("MCQ Question is fetched successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(question);
    }

    @PostMapping()
    public ResponseEntity<MCQQuestion> createQuestion(@RequestBody MCQQuestion question) {
            logger.info("Creating question: {}", question);
            MCQQuestion createdQuestion = questionService.createQuestion(question);
            logger.info("New MCQ Question is added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
       }

    @PutMapping("/{questionId}")
    public ResponseEntity<MCQQuestion> updateQuestion(@PathVariable("questionId") long questionId, @RequestBody MCQQuestion mcqQuestion) {
   
            logger.info("Updating question with id: {}", questionId);
            mcqQuestion.setId(questionId);
            MCQQuestion updatedQuestion = questionService.updateQuestion(mcqQuestion);
            logger.debug("MCQ Question is updated successfully.");
            return ResponseEntity.ok().body(updatedQuestion);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable("questionId") long questionId) {
            logger.info("Deleting question with id: {}", questionId);
            boolean isDeleted = questionService.deleteQuestion(questionId);
            logger.debug("MCQ Question is deleted successfully..",isDeleted);
            return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
    	logger.info("Fetching {} excel file..",file);
        List<MCQQuestion> questions = questionService.uploadQuestions(file);
        logger.info("File is fetched successfully.");
	return ResponseEntity.status(HttpStatus.CREATED).body(questions);
    }
}
