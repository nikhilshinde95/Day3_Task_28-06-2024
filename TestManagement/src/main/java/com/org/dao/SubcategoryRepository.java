package com.org.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.org.entities.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    @Query("SELECT s FROM Subcategory s WHERE s.name = :name")
    List<Subcategory> findByName(@Param("name") String name);

    // You can add more custom queries as needed
}
