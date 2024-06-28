package com.org.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
