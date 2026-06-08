package com.rtrivino.inventory.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtrivino.inventory.dto.ProductDto;
import com.rtrivino.inventory.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventoryController {
    
    private final ProductService productService;

    @GetMapping("/empresa/{nitEmpresa}")
    public List<ProductDto> getMethodName(@PathVariable String nitEmpresa) {
        return productService.findByCompanyNit(nitEmpresa);
    }
    
}
