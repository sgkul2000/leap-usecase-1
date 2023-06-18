package com.lowes.leap.itemmanagement.controller;

import com.lowes.leap.itemmanagement.model.Store;
import com.lowes.leap.itemmanagement.repository.StoreRepository;
import com.lowes.leap.itemmanagement.service.StoreService;
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
@RequestMapping("/api/store")
public class StoreController {

  @Autowired
  StoreRepository storeRepository;

  @Autowired
  private StoreService storeService;

  @GetMapping("/")
  public ResponseEntity<List<Store>> getAllStores() {
    return storeService.getAllStores();
  }

  @PostMapping("/")
  public ResponseEntity<Store> createStore(@RequestBody Store Store) {
    return storeService.createStore(Store);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Store> getStoreById(@PathVariable("id") long id) {
    return storeService.getStoreById(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Store> updateStore(
    @PathVariable("id") long id,
    @RequestBody Store store
  ) {
    return storeService.updateStore(id, store);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteStore(@PathVariable("id") long id) {
    return storeService.deleteStore(id);
  }
}
