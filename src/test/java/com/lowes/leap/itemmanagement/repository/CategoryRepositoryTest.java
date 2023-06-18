package com.lowes.leap.itemmanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.leap.itemmanagement.model.Category;

@DataJpaTest
@Transactional
class CategoryRepositoryTest {

    @Autowired 
    private CategoryRepository categoryRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(categoryRepository).isNotNull();
    }

    @Test
    @DirtiesContext
    void saveCategory() {
        Category category = new Category();
        category.setDescription("test");
        category.setName("Cname");

        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    void getAllCategories() {
        Category category1 = new Category();
        category1.setName("Category1");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Category2");
        categoryRepository.save(category2);

        List<Category> categoryList = categoryRepository.findAll();

        assertThat(categoryList).isNotNull();
        assertThat(categoryList.size()).isEqualTo(2);
    }

    @Test
    void getCategoryById() {
        Category category = new Category();
        category.setDescription("test");
        category.setName("Cname");

        Category savedCategory = categoryRepository.save(category);
        Optional<Category> optionalCategory = categoryRepository.findById(savedCategory.getId());

        assertTrue(optionalCategory.isPresent());
        Category retrievedCategory = optionalCategory.get();
        assertEquals(savedCategory.getId(), retrievedCategory.getId());
        assertEquals(savedCategory.getName(), retrievedCategory.getName());
        assertEquals(savedCategory.getDescription(), retrievedCategory.getDescription());
    }

    @Test
    @DirtiesContext
    void updateCategory() {
        Category category = new Category();
        category.setDescription("test");
        category.setName("Cname");
        Category savedCategory = categoryRepository.save(category);

        savedCategory.setDescription("idk");
        savedCategory.setName("Ram");
        Category updatedCategory = categoryRepository.save(savedCategory);

        assertThat(updatedCategory.getDescription()).isEqualTo("idk");
        assertThat(updatedCategory.getName()).isEqualTo("Ram");
    }

    @Test
    @DirtiesContext
    void deleteCategory() {
        Category category = new Category();
        category.setDescription("test");
        category.setName("Cname");
        Category savedCategory = categoryRepository.save(category);

        categoryRepository.deleteById(savedCategory.getId());
        Optional<Category> categoryOptional = categoryRepository.findById(savedCategory.getId());

        assertThat(categoryOptional).isEmpty();
    }
}
