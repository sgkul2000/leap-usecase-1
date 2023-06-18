package com.lowes.leap.itemmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.lowes.leap.itemmanagement.model.Store;
import com.lowes.leap.itemmanagement.repository.StoreRepository;

@Service
public class StoreService {
	@Autowired
	StoreRepository storeRepository;
	
	public ResponseEntity<List<Store>> getAllStores() {
		try {
			List<Store> stores = new ArrayList<Store>();

			storeRepository.findAll().forEach(stores::add);

			if (stores.isEmpty()) {
				return new ResponseEntity<>(stores, HttpStatus.OK);
			}

			return new ResponseEntity<>(stores, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<Store> createStore(@RequestBody Store Store) {
		try {
			Store _Store = storeRepository.save(new Store(Store.getLocation(), Store.getQuantity()));
			return new ResponseEntity<>(_Store, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<Store> getStoreById(@PathVariable("id") long id) {
		Optional<Store> storeData = storeRepository.findById(id);

		if (storeData.isPresent()) {
			return new ResponseEntity<>(storeData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<Store> updateStore(@PathVariable("id") long id, @RequestBody Store store) {
		Optional<Store> storeData = storeRepository.findById(id);

		if (storeData.isPresent()) {
			Store _store = storeData.get();
			_store.setLocation(store.getLocation());
			_store.setQuantity(store.getQuantity());
			return new ResponseEntity<>(storeRepository.save(_store), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
		public ResponseEntity<HttpStatus> deleteStore(@PathVariable("id") long id) {
		try {
			storeRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
