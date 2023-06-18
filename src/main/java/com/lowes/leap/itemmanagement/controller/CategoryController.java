package com.lowes.leap.itemmanagement.controller;

import com.lowes.leap.itemmanagement.model.Category;
import com.lowes.leap.itemmanagement.repository.CategoryRepository;
import com.lowes.leap.itemmanagement.service.CategoryService;
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
@RequestMapping("/api/category")
public class CategoryController {

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  private CategoryService categoryService;

  @GetMapping("/")
  public ResponseEntity<List<Category>> returnAllCategories() {
    return categoryService.getAllCategorys();
  }

  @PostMapping("/")
  public ResponseEntity<Category> createnewCategory(
    @RequestBody Category Category
  ) {
    return categoryService.createCategory(Category);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable("id") long id) {
    return categoryService.getCategoryById(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Category> updateCategory(
    @PathVariable("id") long id,
    @RequestBody Category Category
  ) {
    return categoryService.updateCategory(id, Category);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteCategory(
    @PathVariable("id") long id
  ) {
    return categoryService.deleteCategory(id);
  }
}
