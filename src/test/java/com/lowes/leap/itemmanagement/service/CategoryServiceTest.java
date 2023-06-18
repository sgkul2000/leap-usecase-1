package com.lowes.leap.itemmanagement.service;

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
import com.lowes.leap.itemmanagement.repository.CategoryRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
@Mock
private CategoryRepository categoryRepository;
@InjectMocks
private CategoryService categoryService;
@Test
// Test for creating a new category
public void createNewCategory() {
	Category category = new Category();
	category.setName("Test");
	category.setId(1);
	category.setDescription("Test description");
	when(categoryRepository.save(ArgumentMatchers.any(Category.class))).thenReturn(category);
	ResponseEntity<Category> savedCategory= categoryService.createCategory(category);
	assertThat(savedCategory).isNotNull();
}
@Test
//test to return all categories
public void returnAllCategories() {
	List<Category> categories  = new ArrayList();
	given(categoryRepository.findAll()).willReturn(categories);
	ResponseEntity<List<Category>> expected = categoryService.getAllCategorys();
	assertEquals(expected.getBody(), categories);
	verify(categoryRepository).findAll();
}
@Test
//test to delete categories
public void deleteCategory(){
	Category category = new Category();
	category.setName("Test");
	category.setId(1L);
	categoryService.deleteCategory(category.getId());
	verify(categoryRepository).deleteById(category.getId());
}

@Test
//test for updating a category by id
public void UpdateCategory() {
	Category category = new Category();
	category.setId(89L);
	category.setName("Test");
	Category newCategory = new Category();
	category.setName("New Test Name");
	categoryService.updateCategory(category.getId(), newCategory);
	verify(categoryRepository).findById(category.getId());
}

@Test
//test to display category by id
public void fetchCategoryByID() {
	Category category = new Category();
	category.setId(89L);
when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
ResponseEntity<Category> expected = categoryService.getCategoryById(category.getId());
assertThat(expected.getBody()).isSameAs(category);
verify(categoryRepository).findById(category.getId());
}

}



