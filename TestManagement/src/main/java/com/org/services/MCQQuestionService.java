package com.org.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.org.entities.MCQQuestion;

public interface MCQQuestionService {
	
	public List<MCQQuestion> getAllQuestions();

	public MCQQuestion getQuestionById(long questionId);

	public MCQQuestion createQuestion(MCQQuestion mcqQuestion);

	public MCQQuestion updateQuestion(MCQQuestion mcqQuestion);

	public boolean deleteQuestion(long questionId);
	
	public List<MCQQuestion> uploadQuestions(MultipartFile file);
}
