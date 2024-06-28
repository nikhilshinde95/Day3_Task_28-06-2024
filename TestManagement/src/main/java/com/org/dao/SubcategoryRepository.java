package com.org.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.entities.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
}
