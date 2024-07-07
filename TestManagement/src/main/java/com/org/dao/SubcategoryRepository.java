package com.org.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.entities.Subcategory;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    @Query("SELECT s FROM Subcategory s WHERE s.name = :name")
    List<Subcategory> findByName(@Param("name") String name);

    @Query("SELECT s FROM Subcategory s WHERE s.name = :name and s.description = :description")
	 Optional<Subcategory> findByNameandDescription(@Param("name") String name, @Param("description") String description);
    
}
