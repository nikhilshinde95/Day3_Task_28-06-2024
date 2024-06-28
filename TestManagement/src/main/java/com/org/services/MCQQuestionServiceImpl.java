package com.org.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.org.dao.MCQDao;
import com.org.entities.MCQQuestion;

@Service
public class MCQQuestionServiceImpl implements MCQService {

    private static final Logger logger = LoggerFactory.getLogger(MCQQuestionServiceImpl.class);

    @Autowired
    private MCQDao mcqDao;

    @Override
    public List<MCQQuestion> getAllQuestions() {
        logger.info("Fetching all questions from database");
        return mcqDao.findAll();
    }

    @Override
    public MCQQuestion getQuestionById(long questionId) {
        logger.info("Fetching question with id: {}", questionId);
        Optional<MCQQuestion> questionById = mcqDao.findById(questionId);
        if (questionById.isPresent()) {
            return questionById.get();
        } else {
            String errorMessage = "Question not found with id: " + questionId;
            logger.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    @Override
    public MCQQuestion createQuestion(MCQQuestion mcqQuestion) {
        logger.info("Creating new question: {}", mcqQuestion);
        MCQQuestion saveQuestion = mcqDao.save(mcqQuestion);
        return saveQuestion;
    }

    @Override
    public MCQQuestion updateQuestion(MCQQuestion mcqQuestion) {
        logger.info("Updating question: {}", mcqQuestion);
        MCQQuestion updateQuestion = mcqDao.save(mcqQuestion);
        return updateQuestion;
    }

    @Override
    public boolean deleteQuestion(long questionId) {
        try {
            logger.info("Deleting question with id: {}", questionId);
            Optional<MCQQuestion> questionOpt = mcqDao.findById(questionId);
            if (questionOpt.isPresent()) {
                mcqDao.delete(questionOpt.get());
                return true;
            } else {
                logger.warn("Question not found with id: {}", questionId);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error occurred while deleting question with id: {}. Error message: {}", questionId, e.getMessage());
            throw new RuntimeException("Error occurred while deleting question with id: " + questionId, e);
        }
    }
}
