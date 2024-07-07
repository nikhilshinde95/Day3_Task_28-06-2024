package com.org.services.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.org.dao.CategoryRepository;
import com.org.dao.MCQQuestionRepository;
import com.org.dao.SubcategoryRepository;
import com.org.entities.Category;
import com.org.entities.MCQQuestion;
import com.org.entities.Subcategory;
import com.org.exception.CategoryNotFoundException;
import com.org.exception.EmptyDataFound;
import com.org.exception.EmptyDatabaseException;
import com.org.exception.EmptyFileException;
import com.org.services.MCQQuestionService;

@Service
public class MCQQuestionServiceImpl implements MCQQuestionService {

    private static final Logger logger = LoggerFactory.getLogger(MCQQuestionServiceImpl.class);

    @Autowired
    private MCQQuestionRepository mcqDao;
    
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<MCQQuestion> getAllQuestions() {
        logger.info("Fetching all questions from database");
        List<MCQQuestion> allQuestions = mcqDao.findAll();
        if(allQuestions.isEmpty()) {
        	throw new EmptyDatabaseException("No questions found in database");
        }
        return allQuestions;
    }

    @Override
    public MCQQuestion getQuestionById(long questionId) {
        logger.info("Fetching question with id: {}", questionId);
        Optional<MCQQuestion> questionById = mcqDao.findById(questionId);
        if (questionById.isPresent()) {
            return questionById.get();
        } 
            logger.error("MCQ Question ID is not found in database");
            throw new CategoryNotFoundException("Entered ID is not present in database");
    }

    @Override
    public MCQQuestion createQuestion(MCQQuestion mcqQuestion) {
        logger.info("Creating new question: {}", mcqQuestion);
        Optional<Subcategory> subCategoryOpt = subcategoryRepository.findById(mcqQuestion.getSubcategory().getId());
        Subcategory subcategory = subCategoryOpt.get();
        mcqQuestion.setSubcategory(subcategory);
        if(mcqQuestion.getQuestion()==null) {
        	throw new EmptyDataFound("Data is empty.");
        }
        MCQQuestion saveQuestion = mcqDao.save(mcqQuestion);
        return saveQuestion;
    }

    @Override
    public MCQQuestion updateQuestion(MCQQuestion mcqQuestion) {
        logger.info("Updating question: {}", mcqQuestion);
        
        Optional<MCQQuestion> mcqQuestionId = mcqDao.findById(mcqQuestion.getId());
        if(mcqQuestionId.isPresent()) {
        	MCQQuestion updateQuestion = mcqDao.save(mcqQuestion);
        	logger.debug("MCQ Question is updated successfully.");
        	 return updateQuestion;
        }
        logger.error("ID is not present in database.");
        throw new CategoryNotFoundException("ID is not present in database");
           
    }

    @Override
    public boolean deleteQuestion(long questionId) {
            logger.info("Deleting question with id: {}", questionId);
            Optional<MCQQuestion> questionOpt = mcqDao.findById(questionId);
            if (questionOpt.isPresent()) {
            	logger.debug("MCQ Question is deleted successfully..");
                mcqDao.delete(questionOpt.get());
                return true;
            }
            logger.warn("Question {} ID is not found", questionId);
            throw new CategoryNotFoundException("Entered ID is not exits in the Database.");
    }
    
    public List<MCQQuestion> uploadQuestions(MultipartFile file) {
    	
    	if(file.isEmpty()) {
    		throw  new EmptyFileException("Fetched file is Empty.");
    	}
    	
        List<MCQQuestion> questions = new ArrayList<>();
        List<MCQQuestion> dbCreatedQuestions = new ArrayList<>();
        Workbook workbook = null;

        try {
            workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0); // Assuming first sheet is used

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Skip header row
                    continue;
                }
                
                //Getting data from excel sheet and setting to MCQQuestion entity table 
                MCQQuestion question = new MCQQuestion();
                question.setQuestion(getStringValueFromCell(row.getCell(3)));
                question.setOptionOne(getStringValueFromCell(row.getCell(4)));
                question.setOptionTwo(getStringValueFromCell(row.getCell(5)));
                question.setOptionThree(getStringValueFromCell(row.getCell(6)));
                question.setOptionFour(getStringValueFromCell(row.getCell(7)));
                question.setCorrectOption(getStringValueFromCell(row.getCell(8)));
                question.setPositiveMark(getIntegerValueFromCell(row.getCell(9)));
                question.setNegativeMark(getIntegerValueFromCell(row.getCell(10)));

                // Retrieve Category
                String categoryName = getStringValueFromCell(row.getCell(1));
                List<Category> category = categoryRepository.findByName(categoryName);
                if (category.isEmpty()) {
                	return new ArrayList<MCQQuestion>();
                }

                // Retrieve Subcategory
                String subcategoryName = getStringValueFromCell(row.getCell(2));
                //Custom Query
                List<Subcategory> subcategory = subcategoryRepository.findByName(subcategoryName);
                if (subcategory.isEmpty()) {
                	return new ArrayList<MCQQuestion>();
                }

                question.setSubcategory(subcategory.getFirst());
                questions.add(question);  //Storing all MCQ Question in questions array
            }
            workbook.close();
            // Save MCQQuestions and return saved instances
            for (MCQQuestion question : questions) {
                MCQQuestion createdQuestion = createQuestion(question);
                dbCreatedQuestions.add(createdQuestion);
            }
        } catch (IOException e) {
            logger.error("Error occurred while uploading questions from file", e);
            throw new RuntimeException("Error occurred while uploading questions from file", e);
        }finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    logger.error("Error closing workbook", e);
                }
            }
        }
        return dbCreatedQuestions; //return all database Questions
    }

    private String getStringValueFromCell(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim(); // Trim to remove leading/trailing spaces
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue()); // Convert numeric value to string
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()); // Convert boolean value to string
            case FORMULA:
                return cell.getCellFormula(); // Return formula as string
            default:
                return null; // Handle other cell types as needed
        }
    }

    private int getIntegerValueFromCell(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return 0; // Default value if cell is blank
        }

        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue(); // Cast double to int
        }

        return 0; // Default to 0 for other types (should ideally handle exceptions or errors)
    }
}
