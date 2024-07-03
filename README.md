# TestManagement Project

## Overview

TestManagement is a Spring Boot project that manages multiple-choice questions (MCQs). 
It includes features for creating, reading, getting specific, updating, and deleting MCQ questions. 
The project uses PostgreSQL as the database and follows standard Java and SQL naming conventions.

## Features : 
- CRUD operations for Categories and MCQ Questions.
- Uploading MCQ Questions from Excel files.
- Association between Category, Subcategory, and MCQ Question entities.

## GitHub Clone Link

Getting Started Clone the repository to your local machine: https://github.com/nikhilshinde95/Day3_Task_28-06-2024.git

## Technologies Used

- Java 21
- Spring Boot 
- PostgreSQL
- JPA (Hibernate)
- SLF4J for logging
- JUnit 5 for testing
- Mockito for mocking

## Project Structure

- **Controller**: Handles HTTP requests and responses.
- **Service**: Contains business logic.
- **Repository**: Manages data access using JPA.
- **Entity**: Represents the MCQ question table in the database.
- **Logging**: Implemented using SLF4J.
- **Exception Handling**: Custom exceptions.
- **Testing**: Unit tests using JUnit 5 and Mockito.

## Setup Instructions

### Prerequisites

- Java 21
- Gradle
- PostgreSQL 

### Database Setup

1. Install PostgreSQL.
2. Create a database named `TestManagementDB`.
3. Update the `application.properties` file with your PostgreSQL credentials.

## Feature Added: MultipleChoiceQuestionTest

### 1. Category and Subcategory Tables

#### Category Table
```sql
category_id   category_name    category_description
---------------------------------------------------
1             Java             Core Java category
2             SQL              Database SQL category
3             Spring Boot      Spring Boot Framework category

#### Subcategory Table

subcategory_id   category_id   subcategory_name      subcategory_description
-----------------------------------------------------------------------------
1                1             Collection            Collections from Java
2                1             Exception Handling    Exception Handling from Java
3                2             Join                  Table joins from SQL
4                3             Annotation            Annotations in Spring

### Update MCQ Question Table : 
{
  "subcategory_id": "4",
  "question": "In Spring Boot @RestController annotation is equivalent to",
  "optionOne": "@Controller and @PostMapping",
  "optionTwo": "@Controller and @Component",
  "optionThree": "@Controller and @ResponseBody",
  "optionFour": "@Controller and @ResponseStatus",
  "correctOption": "@Controller and @ResponseBody",
  "positiveMark": "3",
  "negativeMark": "-1"
}

