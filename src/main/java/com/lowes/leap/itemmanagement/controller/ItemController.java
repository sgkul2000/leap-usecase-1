package com.lowes.leap.itemmanagement.controller;

import com.lowes.leap.itemmanagement.model.Item;
import com.lowes.leap.itemmanagement.repository.CategoryRepository;
import com.lowes.leap.itemmanagement.repository.ItemRepository;
import com.lowes.leap.itemmanagement.repository.StoreRepository;
import com.lowes.leap.itemmanagement.service.ItemService;
import java.util.List;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/item")
public class ItemController {

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  StoreRepository storeRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  private ItemService ItemService;

  @GetMapping("/")
  public ResponseEntity<List<Item>> getAllItems() {
    return ItemService.getAllItems();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Item> getStoreById(@PathVariable("id") long id) {
    return ItemService.getStoreById(id);
  }

  //	public ResponseEntity<Item> createItem(@PathVariable(value = "storeId") Long storeId, @PathVariable(value = "categoryId") Long categoryId, @RequestBody Item item) {
  //		try {
  //			Item _Item = storeRepository.findById(storeId).map((store) -> {
  //				item.setStore(store);
  //				return categoryRepository.findById(categoryId).map((category) -> {
  //					item.setCategory(category);
  //					return itemRepository.save(item);
  //				}).orElse(null);
  //			}).orElse(null);
  //			if(_Item == null)
  //				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  //			return new ResponseEntity<>(_Item, HttpStatus.CREATED);
  //		} catch (Exception e) {
  //			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  //		}
  //	}

  @PostMapping("/{storeId}/{categoryId}/")
  public ResponseEntity<Item> createItem(
    @PathVariable(value = "storeId") Long storeId,
    @PathVariable(value = "categoryId") Long categoryId,
    @RequestBody Item item
  ) {
    return ItemService.createItem(storeId, categoryId, item);
  }

  @PutMapping("/{id}/")
  public ResponseEntity<Item> updateStore(
    @PathVariable("id") long id,
    @RequestBody Item item
  ) {
    return ItemService.updateStore(id, item);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteStore(@PathVariable("id") long id) {
    return ItemService.deleteStore(id);
  }
}
