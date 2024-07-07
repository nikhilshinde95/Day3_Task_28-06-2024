package com.org.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends Exception {
	
	@ExceptionHandler(EmptyDatabaseException.class)
	public ResponseEntity<Object> EmptyDatabaseException(EmptyDatabaseException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<Object> CategoryNotFoundException(CategoryNotFoundException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(DuplicateDataFound.class)
	public ResponseEntity<Object> DuplicateCategoryFound(DuplicateDataFound ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(EmptyDataFound.class)
	public ResponseEntity<Object> EmptyDataFound(EmptyDataFound ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
	
	@ExceptionHandler(EmptyFileException.class)
	public ResponseEntity<Object> EmptyFileException(EmptyFileException ex){
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
	}

}
