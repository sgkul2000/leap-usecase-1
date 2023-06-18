package com.lowes.leap.itemmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lowes.leap.itemmanagement.model.Item;
import com.lowes.leap.itemmanagement.model.Store;

import jakarta.transaction.Transactional;

public interface ItemRepository extends JpaRepository<Item, Long> {
  List<Store> findByName(String location);
  List<Store> findByPrice(Integer quantity);

  List<Item> findByStoreId(Long storeId);
  
  @Transactional
  void deleteByStoreId(long storeId);
}