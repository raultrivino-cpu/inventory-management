package com.rtrivino.inventory.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rtrivino.inventory.dto.PurchaseOrderDto;
import com.rtrivino.inventory.entity.PurchaseOrder;
import com.rtrivino.inventory.exception.ElementNotFoundException;
import com.rtrivino.inventory.mapper.PurchaseOrderMapper;
import com.rtrivino.inventory.repository.PurchaseOrderRepository;
import com.rtrivino.inventory.service.PurchaseOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService{

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;

    @Override
    public PurchaseOrderDto save(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder order = purchaseOrderMapper.toEntity(purchaseOrderDto);
        PurchaseOrder orderDb = purchaseOrderRepository.save(order);

        return purchaseOrderMapper.toDto(orderDb);
    }

    @Override
    public List<PurchaseOrderDto> findAll() {
        return purchaseOrderRepository.findAll()
            .stream()
            .map(purchaseOrderMapper::toDto)
            .toList();      
    }

    @Override
    public PurchaseOrderDto findById(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Orden no encontrada")); 
        return purchaseOrderMapper.toDto(purchaseOrder);
    }

    @Override
    public void delete(Long id) {
        purchaseOrderRepository.deleteById(id);
    }

    @Override
    public PurchaseOrderDto update(Long id, PurchaseOrderDto ordenDto) {
        PurchaseOrder purchaseOrderDb = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Orden no encontrada con id: " + id));

        PurchaseOrder purchaseOrderEntity = purchaseOrderMapper.toEntity(ordenDto);

        purchaseOrderDb.setFecha(purchaseOrderEntity.getFecha());
        purchaseOrderDb.setCliente(purchaseOrderEntity.getCliente());
        purchaseOrderDb.setProducts(purchaseOrderEntity.getProducts());

        PurchaseOrder saved = purchaseOrderRepository.save(purchaseOrderDb);

        return purchaseOrderMapper.toDto(saved);
    }

}
