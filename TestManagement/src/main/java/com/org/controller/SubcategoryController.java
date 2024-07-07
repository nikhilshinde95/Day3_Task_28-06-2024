package com.org.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.org.entities.Subcategory;
import com.org.services.SubcategoryService;

@RestController
@RequestMapping("/api/subcategories")
public class SubcategoryController {

    private static final Logger logger = LoggerFactory.getLogger(SubcategoryController.class);

    @Autowired
    private SubcategoryService subcategoryService;

    @GetMapping()
    public List<Subcategory> getAllSubcategories() {
        logger.info("Fetching all subcategories");
        List<Subcategory> allSubcategories = subcategoryService.getAllSubcategories();
        logger.info("All Subcategories are Fetched Successfully...");
        return allSubcategories;
    }

    @GetMapping("/{subcategoryId}")
    public ResponseEntity<Subcategory> getSubcategoryById(@PathVariable("subcategoryId") Long subcategoryId) {
    	
            logger.info("Fetching subcategory with id: {}", subcategoryId);
            Subcategory subcategory = subcategoryService.getSubcategoryById(subcategoryId);
            logger.debug("Subcategory is Fetched Successfully..");
              return ResponseEntity.ok().body(subcategory);
    }

    @PostMapping()
    public ResponseEntity<Object> createSubcategory(@RequestBody Subcategory subcategory) {
      
            logger.info("Creating subcategory: {}", subcategory);
            Subcategory createdSubcategory = subcategoryService.createSubcategory(subcategory);
            logger.info("Subcategory is created successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSubcategory);
    }

    @PutMapping("/{subcategoryId}")
    public ResponseEntity<Subcategory> updateSubcategory(@PathVariable("subcategoryId") Long subcategoryId,
            @RequestBody Subcategory subcategory) {
            subcategory.setId(subcategoryId);
            logger.info("Updating subcategory with id: {}", subcategoryId);
            Subcategory updatedSubcategory = subcategoryService.updateSubcategory(subcategory);
            return ResponseEntity.status(HttpStatus.OK).body(updatedSubcategory);
    }

    @DeleteMapping("/{subcategoryId}")
    public ResponseEntity<HttpStatus> deleteSubcategory(@PathVariable("subcategoryId") Long subcategoryId) {

            logger.info("Deleting subcategory with id: {}", subcategoryId);
            subcategoryService.deleteSubcategory(subcategoryId);
            logger.info("Subcategory is Deleted Successfully..");
            return new ResponseEntity<>(HttpStatus.OK);
    }
}
