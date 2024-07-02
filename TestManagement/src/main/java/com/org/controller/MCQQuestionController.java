package com.org.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.entities.MCQQuestion;
import com.org.services.MCQQuestionServiceImpl;
import com.org.services.MCQService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class MCQQuestionController {

    private static final Logger logger = LoggerFactory.getLogger(MCQQuestionController.class);

    @Autowired
    private MCQService questionService;

    @GetMapping("/questions")
    public List<MCQQuestion> getAllQuestions() {
        logger.info("Fetching all questions");
        List<MCQQuestion> allQuestion = questionService.getAllQuestions();
        return allQuestion;
    }

    @GetMapping("/questions/{questionId}")
    public ResponseEntity<MCQQuestion> getQuestionById(@PathVariable("questionId") long questionId) {
        try {
            logger.info("Fetching question with id: {}", questionId);
            MCQQuestion question = questionService.getQuestionById(questionId);
            return ResponseEntity.ok().body(question);
        } catch (Exception e) {
            logger.error("Error occurred while fetching question with id: {}. Error message: {}", questionId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/questions")
    public ResponseEntity<MCQQuestion> createQuestion(@RequestBody MCQQuestion question) {
        try {
            logger.info("Creating question: {}", question);
            MCQQuestion createdQuestion = questionService.createQuestion(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
        } catch (Exception e) {
            logger.error("Error occurred while creating question. Error message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/questions/{questionId}")
    public ResponseEntity<MCQQuestion> updateQuestion(@PathVariable("questionId") long questionId, @RequestBody MCQQuestion mcqQuestion) {
        try {
            logger.info("Updating question with id: {}", questionId);
            MCQQuestion updatedQuestion = questionService.updateQuestion(mcqQuestion);
            return ResponseEntity.ok().body(updatedQuestion);
        } catch (Exception e) {
            logger.error("Error occurred while updating question with id: {}. Error message: {}", questionId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable("questionId") long questionId) {
        try {
            logger.info("Deleting question with id: {}", questionId);
            boolean isDeleted = questionService.deleteQuestion(questionId);
            if (isDeleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid question id format. Error message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error occurred while deleting question with id: {}. Error message: {}", questionId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<MCQQuestion> questions = questionService.uploadQuestions(file);
        if (questions.isEmpty()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to create Mcq Question from Excel provided Category or Subcategory not found ");
        }
		return ResponseEntity.status(HttpStatus.CREATED).body(questions);
    }
}
