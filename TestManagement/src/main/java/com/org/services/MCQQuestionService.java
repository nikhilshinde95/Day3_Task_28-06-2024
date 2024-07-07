package com.org.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.org.entities.MCQQuestion;

public interface MCQQuestionService {
	
	List<MCQQuestion> getAllQuestions();

	MCQQuestion getQuestionById(long questionId);

	MCQQuestion createQuestion(MCQQuestion mcqQuestion);

	MCQQuestion updateQuestion(MCQQuestion mcqQuestion);

	boolean deleteQuestion(long questionId);
	
	public List<MCQQuestion> uploadQuestions(MultipartFile file);
}
