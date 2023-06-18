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

import com.lowes.leap.itemmanagement.model.Category;
import com.lowes.leap.itemmanagement.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	public ResponseEntity<List<Category>> getAllCategorys() {
		try {
			List<Category> categorys = new ArrayList<Category>();

			categoryRepository.findAll().forEach(categorys::add);

			if (categorys.isEmpty()) {
				return new ResponseEntity<>(categorys, HttpStatus.OK);
			}

			return new ResponseEntity<>(categorys, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	public ResponseEntity<Category> createCategory(@RequestBody Category Category) {
		try {
			Category _category = categoryRepository.save(new Category(Category.getName(), Category.getDescription()));
			return new ResponseEntity<>(_category, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") long id) {
		Optional<Category> CategoryData = categoryRepository.findById(id);

		if (CategoryData.isPresent()) {
			return new ResponseEntity<>(CategoryData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<Category> updateCategory(@PathVariable("id") long id, @RequestBody Category Category) {
		Optional<Category> CategoryData = categoryRepository.findById(id);

		if (CategoryData.isPresent()) {
			Category _category = CategoryData.get();
			_category.setName(Category.getName());
			_category.setDescription(Category.getDescription());
			return new ResponseEntity<>(categoryRepository.save(_category), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") long id) {
		try {
			categoryRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
