package com.rtrivino.inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rtrivino.inventory.dto.PurchaseOrderDto;
import com.rtrivino.inventory.service.PurchaseOrderService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller responsible for purchase order management operations.
 *
 * <p>
 * Purchase orders represent customer transactions within the business
 * domain. A purchase order can be associated with a customer and multiple
 * products, according to the relational model required by the application.
 * </p>
 *
 * <p>
 * This controller exposes standard CRUD endpoints for purchase orders.
 * Endpoint access is restricted through the centralized Spring Security
 * configuration.
 * </p>
 */
@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    @GetMapping
    public List<PurchaseOrderDto> findAll() {
        return purchaseOrderService.findAll();
    }

    @GetMapping("/{id}")
    public PurchaseOrderDto findById(@PathVariable Long id) {
        return purchaseOrderService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrderDto create(@RequestBody PurchaseOrderDto orden) {
        return purchaseOrderService.save(orden);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        purchaseOrderService.delete(id);
    }

    @PutMapping("/{id}")
    public PurchaseOrderDto update(@PathVariable Long id, @RequestBody PurchaseOrderDto orden) {
        return purchaseOrderService.update(id, orden);
    }
}
