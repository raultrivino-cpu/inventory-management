package com.rtrivino.inventory.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rtrivino.inventory.entity.Category;
import com.rtrivino.inventory.exception.ElementNotFoundException;
import com.rtrivino.inventory.repository.CategoryRepository;
import com.rtrivino.inventory.service.CategoryService;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation responsible for category business operations.
 *
 * <p>
 * Categories are used to classify products within the inventory system.
 * This service provides the application logic for creating, retrieving,
 * updating and deleting product categories.
 * </p>
 *
 * <p>
 * When a requested category cannot be found, the service raises a controlled
 * exception so the global exception handler can return a consistent API
 * response.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Categoria no encontrada"));

    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category update(Long id, Category categoria) {
        Category categoryDb = categoryRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Categoria no encontrada con id: " + id));

        categoryDb.setNombre(categoria.getNombre());
        categoryDb.setCaracteristicas(categoria.getCaracteristicas());

        return categoryRepository.save(categoryDb);
    }
}
