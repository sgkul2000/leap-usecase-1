package com.lowes.leap.itemmanagement.repository;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.lowes.leap.itemmanagement.model.Category;
import com.lowes.leap.itemmanagement.model.Item;
import com.lowes.leap.itemmanagement.model.Store;
import com.lowes.leap.itemmanagement.repository.CategoryRepository;
import com.lowes.leap.itemmanagement.repository.ItemRepository;
import com.lowes.leap.itemmanagement.repository.StoreRepository;

@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        // Create and save sample store
        Store store = new Store();
        store.setLocation("Store1");
        storeRepository.save(store);

        // Create and save sample category
        Category category = new Category();
        category.setName("Category1");
        categoryRepository.save(category);

        // Create and save sample item
        Item item = new Item();
        item.setName("Item1");
        item.setPrice(10);
        item.setStore(store);
        item.setCategory(category);
        item.setStatus(Item.Status.ACTIVE);
        itemRepository.save(item);
    }

    @Test
    
    public void getItemByID() {
        // Retrieve the item by ID from the repository
        Optional<Item> optionalItem = itemRepository.findById(1L);

        // Verify the output
        assertTrue(optionalItem.isPresent());
        Item retrievedItem = optionalItem.get();
        assertEquals("Item1", retrievedItem.getName());
        assertEquals(10, retrievedItem.getPrice());
        assertEquals("Store1", retrievedItem.getStore().getLocation());
        assertEquals("Category1", retrievedItem.getCategory().getName());
        assertEquals(Item.Status.ACTIVE, retrievedItem.getStatus());
    }

    @Test
    
    public void getAllItems() {
        // Retrieve all items from the repository
        List<Item> itemList = itemRepository.findAll();

        // Verify the output
        assertThat(itemList).isNotNull();
        assertThat(itemList.size()).isEqualTo(1);

        Item retrievedItem = itemList.get(0);
        assertEquals("Item1", retrievedItem.getName());
        assertEquals(10, retrievedItem.getPrice());
        assertEquals("Store1", retrievedItem.getStore().getLocation());
        assertEquals("Category1", retrievedItem.getCategory().getName());
        assertEquals(Item.Status.ACTIVE, retrievedItem.getStatus());
    }

    @Test
    public void createItem() {
        // Create a new item
        Item newItem = new Item();
        newItem.setName("NewItem");
        newItem.setPrice(20);
        newItem.setStatus(Item.Status.INACTIVE);

        // Create and save a sample store
        Store store = new Store();
        store.setLocation("Store1");
        storeRepository.save(store);
        newItem.setStore(store);

        // Create and save a sample category
        Category category = new Category();
        category.setName("Category1");
        categoryRepository.save(category);
        newItem.setCategory(category);

        // Save the new item to the repository
        Item savedItem = itemRepository.save(newItem);

        // Verify the output
        assertThat(savedItem).isNotNull();
        assertEquals("NewItem", savedItem.getName());
        assertEquals(20, savedItem.getPrice());
        assertEquals(Item.Status.INACTIVE, savedItem.getStatus());
    }


    @Test
    
    public void deleteItemById() {
        // Delete the item from the repository
        itemRepository.deleteById(1L);

        // Verify that the item is deleted
        Optional<Item> optionalItem = itemRepository.findById(1L);
        assertTrue(optionalItem.isEmpty());
    }
    
    
    @Test
    public void updateItemByID() {
        // Create a new item
        Item newItem = new Item();
        newItem.setName("NewItem");
        newItem.setPrice(20);
        newItem.setStatus(Item.Status.INACTIVE);

        // Create and save a sample store
        Store store = new Store();
        store.setLocation("Store1");
        storeRepository.save(store);
        newItem.setStore(store);

        // Create and save a sample category
        Category category = new Category();
        category.setName("Category1");
        categoryRepository.save(category);
        newItem.setCategory(category);

        // Save the new item to the repository
        Item savedItem = itemRepository.save(newItem);

        // Update the item
        savedItem.setName("UpdatedItem");
        savedItem.setPrice(30);
        savedItem.setStatus(Item.Status.ACTIVE);

        // Update the item in the repository
        Item updatedItem = itemRepository.save(savedItem);

        // Verify the output
        assertThat(updatedItem).isNotNull();
        assertEquals("UpdatedItem", updatedItem.getName());
        assertEquals(30, updatedItem.getPrice());
        assertEquals(Item.Status.ACTIVE, updatedItem.getStatus());
    }

}
