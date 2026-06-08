package com.rtrivino.inventory.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String nombre;
    private String caracteristicas;
    private BigDecimal precioPesos;
    private BigDecimal precioDolares;
    private BigDecimal precioEuros;
    private String nitEmpresa;
    private String nombreEmpresa;
    private List<CategoryDto> categorias;
   
    public ProductDto() {
    }

    public ProductDto(Long id, String nombre, String caracteristicas, BigDecimal precioPesos, BigDecimal precioDolares,
            BigDecimal precioEuros, String nitEmpresa, String nombreEmpresa,  List<CategoryDto> categorias) {
        this.id = id;
        this.nombre = nombre;
        this.caracteristicas = caracteristicas;
        this.precioPesos = precioPesos;
        this.precioDolares = precioDolares;
        this.precioEuros = precioEuros;
        this.nitEmpresa = nitEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.categorias = categorias;
    }

}
