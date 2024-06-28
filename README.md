TestManagement Project
Overview
TestManagement is a Spring Boot project designed for managing multiple-choice questions (MCQs). It includes features for creating, retrieving, updating, and deleting MCQ questions. The project utilizes PostgreSQL as the database and adheres to standard Java and SQL naming conventions.

GitHub Clone Link
Clone the repository to your local machine:

bash
Copy code
git clone https://github.com/nikhilshinde95/Day2_Task1_27-06-2024
Technologies Used
Java 21
Spring Boot
PostgreSQL
Spring Data JPA (Hibernate)
SLF4J for logging
JUnit 5 and Mockito for testing
Project Structure
The project is structured as follows:

Controller: Handles HTTP requests and responses.
Service: Implements business logic.
Repository: Manages data access using Spring Data JPA.
Entity: Represents database tables using JPA annotations.
Logging: Implemented using SLF4J for comprehensive logging.
Exception Handling: Custom exceptions for error management.
Testing: Includes unit tests using JUnit 5 and Mockito for mock testing.
Setup Instructions
Prerequisites
Ensure the following software is installed on your machine:

Java 21
Gradle
PostgreSQL
Database Setup
Install PostgreSQL: Download and install PostgreSQL from official website.
Create Database: Open PostgreSQL and create a new database named TestManagementDB.
Update Configuration: Modify application.properties located in src/main/resources to configure your PostgreSQL database connection properties.
Module/Feature: MultipleChoiceQuestionTest
1. Add Category and Subcategory Tables
Category Table
category_id (primary key)
category_name
category_description
Example:

sql
Copy code
category_id   category_name    category_description
---------------------------------------------------
1             Java             Core Java category
2             SQL              Database SQL category
3             Spring Boot      Spring Boot Framework category
Subcategory Table
subcategory_id (primary key)
category_id (foreign key referencing Category.category_id)
subcategory_name
subcategory_description
Example:

sql
Copy code
subcategory_id   category_id   subcategory_name      subcategory_description
-----------------------------------------------------------------------------
1                1             Collection            Collections from Java
2                1             Exception Handling    Exception Handling from Java
3                2             Join                  Table joins from SQL
4                3             Annotation            Annotations in Spring
2. CRUD Operations for Category and Subcategory
Implement CRUD operations for both Category and Subcategory entities.

3. Update MCQ Question Table
Update the mcq_question table to use subcategory_id instead of category.

Example Input:

json
Copy code
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
MCQ Question Table Columns
question_id (primary key, auto-generated)
subcategory_id (foreign key referencing Subcategory.subcategory_id)
option_one
option_two
option_three
option_four
correct_option
positive_mark
negative_mark
Logging
Logging is implemented using SLF4J, configured in application.properties.

Error Handling
The application handles errors with appropriate HTTP status codes and error messages.
