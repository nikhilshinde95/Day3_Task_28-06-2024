package com.org.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.entities.MCQQuestion;

public interface MCQQuestionRepository extends JpaRepository<MCQQuestion, Long> {

}
