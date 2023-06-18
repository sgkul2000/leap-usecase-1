package com.lowes.leap.itemmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowes.leap.itemmanagement.model.Item;
import com.lowes.leap.itemmanagement.repository.CategoryRepository;
import com.lowes.leap.itemmanagement.repository.ItemRepository;
import com.lowes.leap.itemmanagement.repository.StoreRepository;
import com.lowes.leap.itemmanagement.service.ItemService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.lowes.leap.itemmanagement.model.Item;
import com.lowes.leap.itemmanagement.repository.ItemRepository;
import com.lowes.leap.itemmanagement.repository.StoreRepository;

@Service
public class ItemService {
@Autowired
ItemRepository itemRepository;

@Autowired
StoreRepository storeRepository;

@Autowired
CategoryRepository categoryRepository;
public ResponseEntity<List<Item>> getAllItems() {
	try {
		List<Item> items = new ArrayList<Item>();

		itemRepository.findAll().forEach(items::add);

		if (items.isEmpty()) {
			return new ResponseEntity<>(items, HttpStatus.OK);
		}

		return new ResponseEntity<>(items, HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

public ResponseEntity<Item> getStoreById(@PathVariable("id") long id) {
	Optional<Item> ItemData = itemRepository.findById(id);

	if (ItemData.isPresent()) {
		return new ResponseEntity<>(ItemData.get(), HttpStatus.OK);
	} else {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

public ResponseEntity<Item> createItem(@PathVariable(value = "storeId") Long storeId, @PathVariable(value = "categoryId") Long categoryId, @RequestBody Item item) {
try {
	Item _Item = storeRepository.findById(storeId).map((store) -> {
		item.setStore(store);
		return categoryRepository.findById(categoryId).map((category) -> {
			item.setCategory(category);
			return itemRepository.save(item);
		}).orElse(null);
	}).orElse(null);
	if(_Item == null)
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	return new ResponseEntity<>(_Item, HttpStatus.CREATED);
} catch (Exception e) {
	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
}
}


public ResponseEntity<Item> updateStore(@PathVariable("id") long id, @RequestBody Item item) {
	Optional<Item> itemData = itemRepository.findById(id);

	if (itemData.isPresent()) {
		Item _item = itemData.get();
		_item.setName(item.getName());
		_item.setPrice(item.getPrice());
		_item.setStatus(item.getStatus());

		return new ResponseEntity<>(itemRepository.save(_item), HttpStatus.OK);
	} else {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

public ResponseEntity<HttpStatus> deleteStore(@PathVariable("id") long id) {
	try {
		itemRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	} catch (Exception e) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}


}



