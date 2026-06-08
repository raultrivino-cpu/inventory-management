package com.rtrivino.inventory.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtrivino.inventory.dto.ProductDto;
import com.rtrivino.inventory.service.InventoryPdfService;
import com.rtrivino.inventory.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventoryController {
    
    private final ProductService productService;
    private final InventoryPdfService inventoryPdfService;

    @GetMapping("/empresa/{nitEmpresa}")
    public List<ProductDto> getMethodName(@PathVariable String nitEmpresa) {
        return productService.findByCompanyNit(nitEmpresa);
    }
    
    @GetMapping("/empresa/{nitEmpresa}/pdf")
    public ResponseEntity<byte[]> downloadInventoryPdf(@PathVariable String nitEmpresa) {
        byte[] pdf = inventoryPdfService.generateInventoryPdfByCompany(nitEmpresa);

        String fileName = "Inventario-"+nitEmpresa+".pdf";
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    
}
