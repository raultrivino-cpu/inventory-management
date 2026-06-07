package com.rtrivino.inventory.service;

import java.util.List;

import com.rtrivino.inventory.dto.PurchaseOrderDto;

public interface PurchaseOrderService {
    PurchaseOrderDto save(PurchaseOrderDto purchaseOrder);
    List<PurchaseOrderDto> findAll();
    PurchaseOrderDto findById(Long id);
    void delete(Long id);
    PurchaseOrderDto update(Long id, PurchaseOrderDto orden);
}
