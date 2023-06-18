package com.lowes.leap.itemmanagement.controller;

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
import java.util.ArrayList;

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
import com.lowes.leap.itemmanagement.model.Item;
import com.lowes.leap.itemmanagement.model.Store;
import com.lowes.leap.itemmanagement.repository.CategoryRepository;
import com.lowes.leap.itemmanagement.repository.ItemRepository;
import com.lowes.leap.itemmanagement.repository.StoreRepository;
import com.lowes.leap.itemmanagement.service.ItemService;
import com.lowes.leap.itemmanagement.model.Item;
//import com.lowes.leap.itemmanagement.model.Status;


@WebMvcTest(ItemController.class)
class ItemControllerTest {

	
	@Autowired
	private MockMvc mockmvc;
	
	@MockBean
	ItemRepository itemRepository;

	@MockBean
	StoreRepository storeRepository;

	@MockBean
	CategoryRepository categoryRepository;
	
	@MockBean
	private ItemService ItemService;
	
	@Test
	public void createNewItem() throws Exception {
	    Category category = new Category();
	    Store store = new Store();
	    store.setId(1L);
	    category.setId(1L);
	    Item item = new Item();
	    item.setCategory(category);
	    item.setId(1);
	    item.setName("Test");
	    item.setPrice(10);
	    item.setStatus(null);
	    item.setStore(store);

	    // Mock the service method
	    ResponseEntity<Item> responseEntity = new ResponseEntity<>(item, HttpStatus.CREATED);
	    when(ItemService.createItem(any(Long.class), any(Long.class), any(Item.class))).thenReturn(responseEntity);

	    ResultActions response = mockmvc.perform(post("/api/item/{storeId}/{categoryId}/", 1L, 1L)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(item)));

	    // Then - verify the result or output using assert statements
	    response.andDo(print()).andExpect(status().isCreated());
	}
	
	@Test
	public void getItemById() throws Exception {
		Category category = new Category();
	    Store store = new Store();
	    store.setId(1L);
	    category.setId(1L);
	    Item item = new Item();
	    item.setCategory(category);
	    item.setId(1);
	    item.setName("Test");
	    item.setPrice(10);
	    item.setStatus(null);
	    item.setStore(store);
		 // Mock the service method
	    ResponseEntity<Item> responseEntity = new ResponseEntity<>(item, HttpStatus.OK);
//	    when(categoryService.getCategoryById(any(Long.class))).thenReturn(responseEntity);
	    long itemid = 1L; // Set the specific category ID you want to test
	    when(ItemService.getStoreById(eq(itemid))).thenReturn(responseEntity);

	    ResultActions response = mockmvc.perform(get("/api/item/{id}", 1L)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(category)))
	            .andExpect(status().isOk())
	            .andDo(print())
	            .andExpect(jsonPath("$.id", equalTo(1)))
	            .andExpect(jsonPath("$.name", is(item.getName())));



}
	@Test
	public void getAllItems() throws Exception {
		List<Item> items = new ArrayList<>();
	    
		Item item = new Item();
		item.setId(1);
		item.setName("Example Item");
		item.setPrice(10);
		item.setStatus(Item.Status.ACTIVE);

		// Create store object and set it in the item
		Store store = new Store();
		store.setId(1); // Set the store ID
		item.setStore(store);

		// Create category object and set it in the item
		Category category = new Category();
		category.setId(1); // Set the category ID
		item.setCategory(category);
		items.add(item);
		
		ResponseEntity<List<Item>> responseEntity = new ResponseEntity<>(items, HttpStatus.OK);
	    when(ItemService.getAllItems()).thenReturn(responseEntity);

		ResultActions response = mockmvc.perform(get("/api/item/")
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andDo(print())
	            .andExpect(jsonPath("$[0].name", is(items.get(0).getName())));
	
	}
	
	@Test
	public void deleteItem() throws Exception {		
	Item item = new Item();
	item.setId(1);
	item.setName("Example Item");
	item.setPrice(10);
	item.setStatus(Item.Status.ACTIVE);

	// Create store object and set it in the item
	Store store = new Store();
	store.setId(1); // Set the store ID
	item.setStore(store);

	// Create category object and set it in the item
	Category category = new Category();
	category.setId(1); // Set the category ID
	item.setCategory(category);

	    ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.OK);
	    when(ItemService.deleteStore(1)).thenReturn(responseEntity);

	    mockmvc.perform(delete("/api/item/{id}", item.getId())
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(item)))
	            .andExpect(status().isOk());
	}
	@Test
	public void updateItem() throws Exception {
	    // Create a sample Category object
		Item item = new Item();
		item.setId(1);
		item.setName("Example Item");
		item.setPrice(10);
		item.setStatus(Item.Status.ACTIVE);

		// Create store object and set it in the item
		Store store = new Store();
		store.setId(1); // Set the store ID
		item.setStore(store);

		// Create category object and set it in the item
		Category category = new Category();
		category.setId(1); // Set the category ID
		item.setCategory(category);

	    // Mock the service method to return the created Category
	    ResponseEntity<Item> createResponseEntity = new ResponseEntity<>(item, HttpStatus.CREATED);
	    when(ItemService.createItem(any(Long.class), any(Long.class), any(Item.class))).thenReturn(createResponseEntity);

	    // Perform the request to create the category
	    ResultActions response = mockmvc.perform(post("/api/item/{storeId}/{categoryId}/", 1L, 1L)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(item)));

	    // Then - verify the result or output using assert statements
	    response.andDo(print()).andExpect(status().isCreated()).andReturn();

	    // Update the category with different values
	    Item updatedItem = new Item();
	    updatedItem.setName("NEW ITEM NAME");
	    updatedItem.setId(item.getId());

	    // Mock the service method to return the updated Category
	    ResponseEntity<Item> updateResponseEntity = new ResponseEntity<>(updatedItem, HttpStatus.OK);
	    when(ItemService.updateStore(eq(item.getId()), any(Item.class))).thenReturn(updateResponseEntity);

	    // Perform the request to update the category
	    mockmvc.perform(put("/api/item/{id}/", item.getId())
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(updatedItem)))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.name", is(updatedItem.getName())));
	}

}
