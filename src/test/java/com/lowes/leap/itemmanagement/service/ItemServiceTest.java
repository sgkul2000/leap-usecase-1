package com.lowes.leap.itemmanagement.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.lowes.leap.itemmanagement.model.Category;
import com.lowes.leap.itemmanagement.model.Item;
import com.lowes.leap.itemmanagement.model.Store;
import com.lowes.leap.itemmanagement.repository.ItemRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
	@Mock
	private ItemRepository itemRepository;
	@InjectMocks
	private ItemService itemService;
	@Test
	// Test for creating a new Item
	public void createNewItem() {
		Category category = new Category();
		Store store = new Store();
		Item item = new Item();
		item.setCategory(category);
		item.setId(1);
		item.setName("Test");
		item.setPrice(10);
		item.setStatus(null);
		item.setStore(store);
		ResponseEntity<Item> savedItem= itemService.createItem(null, null, item);
		assertThat(savedItem).isNotNull();
	}
	@Test
	//test to return all items
	public void returnAllItems() {
		List<Item> items  = new ArrayList();
		given(itemRepository.findAll()).willReturn(items);
		ResponseEntity<List<Item>> expected = itemService.getAllItems();
		assertEquals(expected.getBody(), items);
		verify(itemRepository).findAll();
	}
	@Test
	//test to delete items
	public void deleteItem(){
		Category category = new Category();
		Store store = new Store();
		Item item = new Item();
		item.setCategory(category);
		item.setId(1);
		item.setName("Test");
		item.setPrice(10);
		item.setStatus(null);
		item.setStore(store);
		itemService.deleteStore(item.getId());
		verify(itemRepository).deleteById(item.getId());
	}
	@Test
	//test for updating an item by id
	public void UpdateItem() {
		Category category = new Category();
		Store store = new Store();
		Item item = new Item();
		item.setCategory(category);
		item.setId(1);
		item.setName("Test");
		item.setPrice(10);
		item.setStatus(null);
		item.setStore(store);
		
		Item newItem = new Item();
		item.setName("New Test Name");
		itemService.updateStore(item.getId(), newItem);
		verify(itemRepository).findById(item.getId());
	}
	@Test
	//test to display item by id
	public void fetchItemByID() {
		Item item = new Item();
		item.setId(89L);
	when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
	ResponseEntity<Item> expected = itemService.getStoreById(item.getId());
	assertThat(expected.getBody()).isSameAs(item);
	verify(itemRepository).findById(item.getId());
	}
}
