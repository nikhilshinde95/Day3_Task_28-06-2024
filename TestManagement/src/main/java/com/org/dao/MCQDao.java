package com.org.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.entities.MCQQuestion;

public interface MCQDao extends JpaRepository<MCQQuestion, Long> {

}
