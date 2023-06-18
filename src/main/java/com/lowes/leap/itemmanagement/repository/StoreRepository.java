package com.lowes.leap.itemmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lowes.leap.itemmanagement.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
  List<Store> findByLocation(String location);
  List<Store> findByQuantity(Integer quantity);
  }