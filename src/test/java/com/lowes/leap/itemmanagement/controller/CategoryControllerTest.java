package com.lowes.leap.itemmanagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.lowes.leap.itemmanagement.model.Category;
import com.lowes.leap.itemmanagement.repository.CategoryRepository;
import com.lowes.leap.itemmanagement.service.CategoryService;


@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
	@Autowired
	private MockMvc mockmvc;
	
	@MockBean
	CategoryService categoryService;

	@MockBean
	CategoryRepository categoryRepository;
	
	@Test
	public void createNewCategory() throws Exception {
		Category category = new Category();
		category.setName("Test");
		category.setId(1);
		category.setDescription("Test description");
		 // Mock the service method
	    ResponseEntity<Category> responseEntity = new ResponseEntity<>(category, HttpStatus.CREATED);
	    when(categoryService.createCategory(any(Category.class))).thenReturn(responseEntity);

	    ResultActions response = mockmvc.perform(post("/api/category/")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content( new ObjectMapper().writeValueAsString(category)));
	    
	 // then - verify the result or output using assert statements
        response.andDo(print()).andExpect(status().isCreated());
}
	
	@Test
	public void getCategoryById() throws Exception {
		Category category = new Category();
		category.setName("Test");
		category.setId(1L);
		category.setDescription("Test description");
		 // Mock the service method
	    ResponseEntity<Category> responseEntity = new ResponseEntity<>(category, HttpStatus.OK);
//	    when(categoryService.getCategoryById(any(Long.class))).thenReturn(responseEntity);
	    long categoryId = 1L; // Set the specific category ID you want to test
	    when(categoryService.getCategoryById(eq(categoryId))).thenReturn(responseEntity);

	    ResultActions response = mockmvc.perform(get("/api/category/{id}", 1L)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(category)))
	            .andExpect(status().isOk())
	            .andDo(print())
	            .andExpect(jsonPath("$.id", equalTo(1)))
	            .andExpect(jsonPath("$.name", is(category.getName())))
	            .andExpect(jsonPath("$.description", is(category.getDescription())));



}
	
	@Test
	public void getAllCategorys() throws Exception {
		List<Category> categories = Arrays.asList(
				new Category("test","testD"),
				new Category("test2","testD")
				);
		
		ResponseEntity<List<Category>> responseEntity = new ResponseEntity<>(categories, HttpStatus.OK);
	    when(categoryService.getAllCategorys()).thenReturn(responseEntity);

		ResultActions response = mockmvc.perform(get("/api/category/")
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andDo(print())
	            .andExpect(jsonPath("$[0].name", is(categories.get(0).getName())))
	            .andExpect(jsonPath("$[1].name", is(categories.get(1).getName())));
	
	
	}
	
	@Test
	public void deleteCategory() throws Exception {
	    Category category = new Category("test", "testD");
	    category.setId(1);

	    ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.OK);
	    when(categoryService.deleteCategory(1)).thenReturn(responseEntity);

	    mockmvc.perform(delete("/api/category/{id}", category.getId())
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(category)))
	            .andExpect(status().isOk());
	}
	
	@Test
	public void updateCategory() throws Exception {
	    // Create a sample Category object
	    Category category = new Category("Test", "Test Description");

	    // Mock the service method to return the created Category
	    ResponseEntity<Category> createResponseEntity = new ResponseEntity<>(category, HttpStatus.CREATED);
	    when(categoryService.createCategory(any(Category.class))).thenReturn(createResponseEntity);

	    // Perform the request to create the category
	    mockmvc.perform(post("/api/category/")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(category)))
	            .andExpect(status().isCreated())
	            .andReturn();

	    // Update the category with different values
	    Category updatedCategory = new Category("Updated Name", "Updated Description");
	    updatedCategory.setId(category.getId());

	    // Mock the service method to return the updated Category
	    ResponseEntity<Category> updateResponseEntity = new ResponseEntity<>(updatedCategory, HttpStatus.OK);
	    when(categoryService.updateCategory(eq(category.getId()), any(Category.class))).thenReturn(updateResponseEntity);

	    // Perform the request to update the category
	    mockmvc.perform(put("/api/category/{id}", category.getId())
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(updatedCategory)))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.name", is(updatedCategory.getName())))
	            .andExpect(jsonPath("$.description", is(updatedCategory.getDescription())));
	}



	
	
	
	}
