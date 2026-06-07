package com.rtrivino.inventory.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderDto {
    private Long id;
    private LocalDate fecha;
    private Long clienteId;
    private List<Long> productos;
}
