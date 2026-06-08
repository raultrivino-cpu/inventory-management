package com.rtrivino.inventory.service;

public interface InventoryPdfService {
    byte[] generateInventoryPdfByCompany(String nitEmpresa);
}