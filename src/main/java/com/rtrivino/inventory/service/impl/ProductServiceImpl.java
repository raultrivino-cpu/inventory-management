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

/**
 * Service implementation responsible for product business operations.
 *
 * <p>
 * Products are associated with companies and categories. This service
 * provides standard product CRUD operations and supports inventory queries
 * by company NIT.
 * </p>
 *
 * <p>
 * The mapping between DTOs and entities is delegated to the product mapper,
 * which resolves related company and category information.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

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
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Producto no encontrada"));
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

    /**
     * Retrieves products associated with a specific company.
     *
     * <p>
     * This method supports the inventory view, where users select a company
     * and consult the products registered under it.
     * </p>
     *
     * @param nitEmpresa company NIT used to filter products
     * @return products associated with the selected company
     */
    @Override
    public List<ProductDto> findByCompanyNit(String nitEmpresa) {
        return productRepository.findByEmpresaNit(nitEmpresa)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

}
