package com.org.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.org.entities.Category;
import com.org.entities.Subcategory;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	 @Query("SELECT s FROM Category s WHERE s.name = :name")
	    List<Category> findByName(@Param("name") String name);
	 
	 @Query("SELECT s FROM Category s WHERE s.name = :name and s.description = :description")
	 Optional<Category> findByNameandFindByDescription(@Param("name") String name, @Param("description") String description);

}
