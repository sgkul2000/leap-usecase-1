package com.lowes.leap.itemmanagement.controller;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowes.leap.itemmanagement.model.Category;
import com.lowes.leap.itemmanagement.model.Store;
import com.lowes.leap.itemmanagement.repository.StoreRepository;
import com.lowes.leap.itemmanagement.service.StoreService;

@WebMvcTest(StoreController.class)
class StoreControllerTest {
	@Autowired
	private MockMvc mockmvc;

	@MockBean
	StoreService storeService;

	@MockBean
	StoreRepository storeRepository;

	@Test
	public void createNewStore() throws Exception {
		Store store = new Store();
		store.setLocation("Test");
		store.setId(1);
		store.setQuantity(20);
		// Mock the service method
		ResponseEntity<Store> responseEntity = new ResponseEntity<>(store, HttpStatus.CREATED);
		when(storeService.createStore(any(Store.class))).thenReturn(responseEntity);

		ResultActions response = mockmvc.perform(post("/api/store/").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(store)));

		// then - verify the result or output using assert statements
		response.andDo(print()).andExpect(status().isCreated());
	}

	@Test
	public void getStoreById() throws Exception {
		Store store = new Store();
		store.setLocation("Test");
		store.setId(1);
		store.setQuantity(20);
		// Mock the service method
		ResponseEntity<Store> responseEntity = new ResponseEntity<>(store, HttpStatus.OK);
		long storeid = 1L; // Set the specific category ID you want to test
		when(storeService.getStoreById(eq(storeid))).thenReturn(responseEntity);

		ResultActions response = mockmvc
				.perform(get("/api/store/{id}", 1L)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(store)))
				.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.location", is(store.getLocation())))
				.andExpect(jsonPath("$.quantity", is(store.getQuantity())));
	}
	
	@Test
	public void getAllStores() throws Exception {
		List<Store> stores = new ArrayList<>(Arrays.asList(
		        new Store("Delhi", 20),
		        new Store("Mumbai", 15)
		    ));
		
		ResponseEntity<List<Store>> responseEntity = new ResponseEntity<>(stores, HttpStatus.OK);
	    when(storeService.getAllStores()).thenReturn(responseEntity);

		ResultActions response = mockmvc.perform(get("/api/store/")
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andDo(print())
	            .andExpect(jsonPath("$[0].location", is(stores.get(0).getLocation())))
	            .andExpect(jsonPath("$[1].location", is(stores.get(1).getLocation())));
	
	
	}
	
	@Test
	public void deleteCategory() throws Exception {
	    Store store = new Store("Delhi", 20);

	    store.setId(1);

	    ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.OK);
	    when(storeService.deleteStore(1)).thenReturn(responseEntity);

	    mockmvc.perform(delete("/api/store/{id}", store.getId())
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(store)))
	            .andExpect(status().isOk());
	}
	
	@Test
	public void updateStore() throws Exception {
	    // Create a sample Store object
	    Store store = new Store("Delhi", 20);

	    // Mock the service method to return the created Category
	    ResponseEntity<Store> createResponseEntity = new ResponseEntity<>(store, HttpStatus.CREATED);
	    when(storeService.createStore(any(Store.class))).thenReturn(createResponseEntity);

	    // Perform the request to create the category
	    mockmvc.perform(post("/api/store/")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(store)))
	            .andExpect(status().isCreated())
	            .andReturn();

	    // Update the category with different values
	    Store updatedStore = new Store("Banglore",20);
	    updatedStore.setId(store.getId());

	    // Mock the service method to return the updated Category
	    ResponseEntity<Store> updateResponseEntity = new ResponseEntity<>(updatedStore, HttpStatus.OK);
	    when(storeService.updateStore(eq(store.getId()), any(Store.class))).thenReturn(updateResponseEntity);

	    // Perform the request to update the category
	    mockmvc.perform(put("/api/store/{id}", store.getId())
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(updatedStore)))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.location", is(updatedStore.getLocation())))
	            .andExpect(jsonPath("$.quantity", is(updatedStore.getQuantity())));
	}

}