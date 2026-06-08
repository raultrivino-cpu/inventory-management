package com.rtrivino.inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rtrivino.inventory.entity.Category;
import com.rtrivino.inventory.service.CategoryService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller responsible for category management operations.
 *
 * <p>
 * Categories are used to classify products within the inventory system.
 * A product can be associated with one or more categories, allowing the
 * frontend to display product information in a more descriptive and organized
 * way.
 * </p>
 *
 * <p>
 * This controller exposes CRUD endpoints for categories. Access restrictions
 * are handled by the Spring Security configuration according to the user's
 * role.
 * </p>
 */
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category categoria) {
        return categoryService.update(id, categoria);
    }
}
