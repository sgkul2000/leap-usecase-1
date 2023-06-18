package com.lowes.leap.itemmanagement.service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
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
import com.lowes.leap.itemmanagement.model.Store;
import com.lowes.leap.itemmanagement.repository.CategoryRepository;
import com.lowes.leap.itemmanagement.repository.StoreRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
	@Mock
	private StoreRepository storeRepository;
	@InjectMocks
	private StoreService storeService;
	@Test
	// Test for creating a new store
	public void createNewStore() {
		Store store = new Store();
		store.setId(1);
		store.setLocation("Delhi");
		store.setQuantity(20);
		when(storeRepository.save(ArgumentMatchers.any(Store.class))).thenReturn(store);
		ResponseEntity<Store> savedCategory= storeService.createStore(store);
		assertThat(savedCategory).isNotNull();
	}
	
	@Test
	//test to return all stores
	public void returnAllStores() {
		List<Store> stores  = new ArrayList();
		given(storeRepository.findAll()).willReturn(stores);
		ResponseEntity<List<Store>> expected = storeService.getAllStores();
		assertEquals(expected.getBody(), stores);
		verify(storeRepository).findAll();
	}
	
	@Test
	//test to delete stores
	public void deleteStore(){
		Store store = new Store();
		store.setId(1);
		store.setLocation("Delhi");
		store.setQuantity(20);
		storeService.deleteStore(store.getId());
		verify(storeRepository).deleteById(store.getId());
	}
	
	@Test
	//test for updating a category by id
	public void UpdateStore() {
		Store store = new Store();
		store.setId(1);
		store.setLocation("Delhi");
		store.setQuantity(20);
		Store newStore= new Store();
		store.setQuantity(50);
		storeService.updateStore(store.getId(), newStore);
		verify(storeRepository).findById(store.getId());
	}
	@Test
	//test to display category by id
	public void fetchStoreByID() {
		Store store = new Store();
		store.setId(89L);
	when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));
	ResponseEntity<Store> expected = storeService.getStoreById(store.getId());
	assertThat(expected.getBody()).isSameAs(store);
	verify(storeRepository).findById(store.getId());
	}

}
