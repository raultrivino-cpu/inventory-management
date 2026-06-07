package com.rtrivino.inventory.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rtrivino.inventory.dto.ProductDto;
import com.rtrivino.inventory.entity.Product;
import com.rtrivino.inventory.exception.ElementNotFoundException;
import com.rtrivino.inventory.mapper.ProductMapper;
import com.rtrivino.inventory.repository.ProductRepository;
import com.rtrivino.inventory.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Product save(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);

        return productRepository.save(product);
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll()
            .stream()
            .map(productMapper::toDto)
            .toList();
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Producto no encontrada")); 
        return productMapper.toDto(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto update(Long id, ProductDto productoDto) {
        Product productDb = productRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Producto no encontrado con id: " + id));
        
        Product productEntity = productMapper.toEntity(productoDto);

        productDb.setNombre(productEntity.getNombre());
        productDb.setCaracteristicas(productEntity.getCaracteristicas());
        productDb.setPrecioPesos(productEntity.getPrecioPesos());
        productDb.setPrecioDolares(productEntity.getPrecioDolares());
        productDb.setPrecioEuros(productEntity.getPrecioEuros());
        productDb.setEmpresa(productEntity.getEmpresa());
        productDb.setCategorias(productEntity.getCategorias());
        
        Product saved = productRepository.save(productDb); 
        return productMapper.toDto(saved);
    }


}
