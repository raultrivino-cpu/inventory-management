package com.rtrivino.inventory.service;

import java.util.List;

import com.rtrivino.inventory.entity.Category;

public interface CategoryService {
    Category save(Category category);
    List<Category> findAll();
    Category findById(Long id);
    void delete(Long id);
    Category update(Long id, Category categoria);
}
