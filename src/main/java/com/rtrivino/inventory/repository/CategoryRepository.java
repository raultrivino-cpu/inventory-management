package com.rtrivino.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtrivino.inventory.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
