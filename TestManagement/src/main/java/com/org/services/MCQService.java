package com.org.services;

import java.util.List;

import com.org.entities.MCQQuestion;

public interface MCQService {
	
	List<MCQQuestion> getAllQuestions();

	MCQQuestion getQuestionById(long questionId);

	MCQQuestion createQuestion(MCQQuestion mcqQuestion);

	MCQQuestion updateQuestion(MCQQuestion mcqQuestion);

	boolean deleteQuestion(long questionId);
}
