package com.rtrivino.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtrivino.inventory.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

}
