package com.lowes.leap.itemmanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.lowes.leap.itemmanagement.model.Category;
import com.lowes.leap.itemmanagement.model.Store;

@DataJpaTest

class StoreRepositoryTest {
	 @Autowired 
     private StoreRepository storeRepository;
     private Store store;

     @BeforeEach
     public void setup() {
    	 store = new Store();
    	 store.setId(1);
    	 store.setLocation("Cname");
    	 store.setQuantity(100);
    	 storeRepository.save(store);
     }
     
  // JUnit test for save employee operation
     @Test
     public void saveStore(){

         //given - precondition or setup
    	 Store store = new Store();
    	 store.setId(1);
    	 store.setLocation("Cname");
    	 store.setQuantity(100);
         // when - action or the behaviour that we are going test
    	 Store savedStore= storeRepository.save(store);

         // then - verify the output
         assertThat(savedStore).isNotNull();
         assertThat(savedStore.getId()).isGreaterThan(0);
     }
     
     // JUnit test for get all categories operation
     @Test
     public void getAllCategories() {
         Store store1 = new Store();
         store1.setId(1);
         store1.setLocation("Cname1");
         store1.setQuantity(100);

         Store store2 = new Store();
         store2.setId(2);
         store2.setLocation("Cname2");
         store2.setQuantity(100);

         storeRepository.save(store1);
         storeRepository.save(store2);

         List<Store> storeList = storeRepository.findAll();

         assertThat(storeList).isNotNull();
         assertThat(storeList.size()).isEqualTo(2);
     }
     
     @Test
     public void getStoreById() {
         // Create a sample store
         Store store = new Store();
         store.setLocation("Cname");
         store.setQuantity(100);
         store.setId(1);

         // Save the store to the repository
         Store savedStore = storeRepository.save(store);

         // Retrieve the store by ID from the repository
         Optional<Store> optionalStore = storeRepository.findById(savedStore.getId());

         // Verify the output
         assertTrue(optionalStore.isPresent());
         Store retrievedStore = optionalStore.get();
         assertEquals(savedStore.getId(), retrievedStore.getId());
         assertEquals(savedStore.getLocation(), retrievedStore.getLocation());
         assertEquals(savedStore.getQuantity(), retrievedStore.getQuantity());
     }
     
     @Test
     public void UpdateStore(){

         Store temp = storeRepository.save(store);

         // when -  action or the behaviour that we are going test
         Store savedStore = storeRepository.findById(temp.getId()).get();
         savedStore.setLocation("idk");
         savedStore.setQuantity(10);
         Store updatedcategory =  storeRepository.save(savedStore);

         // then - verify the output
         assertThat(updatedcategory.getLocation()).isEqualTo("idk");
         assertThat(updatedcategory.getQuantity()).isEqualTo(10);
     }
     
     
  // JUnit test for delete employee operation
     @Test
     public void deleteStore(){

         Store saved = storeRepository.save(store);

         // when -  action or the behaviour that we are going test
         storeRepository.deleteById(saved.getId());
         Optional<Store> StoreOptional = storeRepository.findById(saved.getId());

         // then - verify the output
         assertThat(StoreOptional).isEmpty();
     }

     
     



}
