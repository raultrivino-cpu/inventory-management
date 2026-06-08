package com.rtrivino.inventory.service;

import java.util.List;

import com.rtrivino.inventory.dto.ProductDto;
import com.rtrivino.inventory.entity.Product;

public interface ProductService {
    Product save(ProductDto productDto);
    List<ProductDto> findAll();
    ProductDto findById(Long id);
    void delete(Long id);
    ProductDto update(Long id, ProductDto productoDto);
    List<ProductDto> findByCompanyNit(String nitEmpresa);
}
