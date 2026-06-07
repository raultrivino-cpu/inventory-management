package com.rtrivino.inventory.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rtrivino.inventory.dto.PurchaseOrderDto;
import com.rtrivino.inventory.entity.Customer;
import com.rtrivino.inventory.entity.Product;
import com.rtrivino.inventory.entity.PurchaseOrder;
import com.rtrivino.inventory.exception.ElementNotFoundException;
import com.rtrivino.inventory.repository.CustomerRepository;
import com.rtrivino.inventory.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PurchaseOrderMapper {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;


    public PurchaseOrder toEntity(PurchaseOrderDto purchaseOrderDto){
        PurchaseOrder order = new PurchaseOrder();

        Customer customerDb = customerRepository.findById(purchaseOrderDto.getClienteId())
            .orElseThrow(() -> new ElementNotFoundException("Cliente no encuentra con id: " + purchaseOrderDto.getClienteId()));

        List<Product> productsDb = productRepository.findAllById(purchaseOrderDto.getProductos());


        order.setFecha(purchaseOrderDto.getFecha());
        order.setCliente(customerDb);
        order.setProducts(productsDb);

        return order;
    }

    public PurchaseOrderDto toDto(PurchaseOrder purchaseOrder){
        PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto();
    
        purchaseOrderDto.setId(purchaseOrder.getId());
        purchaseOrderDto.setFecha(purchaseOrder.getFecha());
        purchaseOrderDto.setClienteId(purchaseOrder.getCliente().getId());
        purchaseOrderDto.setProductos(purchaseOrder.getProducts().stream().map(Product::getId).toList());

        return purchaseOrderDto;
    }

}
