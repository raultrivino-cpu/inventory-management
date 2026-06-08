package com.rtrivino.inventory.mapper;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rtrivino.inventory.dto.CategoryDto;
import com.rtrivino.inventory.dto.ProductDto;
import com.rtrivino.inventory.entity.Category;
import com.rtrivino.inventory.entity.Company;
import com.rtrivino.inventory.entity.Product;
import com.rtrivino.inventory.exception.ElementNotFoundException;
import com.rtrivino.inventory.repository.CategoryRepository;
import com.rtrivino.inventory.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    public Product toEntity(ProductDto productDto) {
        Product producto = new Product();

        Company companyDb = companyRepository.findById(productDto.getNitEmpresa())
                .orElseThrow(() -> new ElementNotFoundException(
                        "Compañía no encontrada con id: " + productDto.getNitEmpresa()));

        List<Long> categoryIds = productDto.getCategorias()
                .stream()
                .map(CategoryDto::getId)
                .toList();

        List<Category> categorias = categoryRepository.findAllById(categoryIds);

        producto.setNombre(productDto.getNombre());
        producto.setCaracteristicas(productDto.getCaracteristicas());
        producto.setPrecioDolares(productDto.getPrecioDolares());
        producto.setPrecioEuros(productDto.getPrecioEuros());
        producto.setPrecioPesos(productDto.getPrecioPesos());
        producto.setEmpresa(companyDb);
        producto.setCategorias(categorias);

        return producto;
    }

    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setNombre(product.getNombre());
        productDto.setCaracteristicas(product.getCaracteristicas());
        productDto.setPrecioDolares(product.getPrecioDolares());
        productDto.setPrecioEuros(product.getPrecioEuros());
        productDto.setPrecioPesos(product.getPrecioPesos());
        productDto.setNitEmpresa(product.getEmpresa().getNit());
        productDto.setNombreEmpresa(product.getEmpresa().getNombre());
        productDto.setCategorias(
                product.getCategorias()
                        .stream().map(category -> new CategoryDto(
                                category.getId(),
                                category.getNombre()))
                        .toList());

        return productDto;
    }
}
