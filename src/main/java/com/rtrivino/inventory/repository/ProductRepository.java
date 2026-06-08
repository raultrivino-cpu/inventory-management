package com.rtrivino.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtrivino.inventory.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Finds all products associated with the given company NIT.
     *
     * <p>
     * This method supports the inventory view, where products are queried
     * by company.
     * </p>
     *
     * @param nitEmpresa company NIT
     * @return products registered under the selected company
     */
    List<Product> findByEmpresaNit(String nitEmpresa);
}
