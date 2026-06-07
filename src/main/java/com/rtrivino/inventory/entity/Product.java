package com.rtrivino.inventory.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_seq")
    @SequenceGenerator(name = "producto_seq", sequenceName = "producto_seq", allocationSize = 1)
    private Long id;

    @Column(name="nombre", nullable = false, unique = true)
    private String nombre;

    private String caracteristicas;

    @Column(name="precio_pesos", nullable = false)
    private BigDecimal precioPesos;

    @Column(name="precio_dolares", nullable = false)
    private BigDecimal precioDolares;

    @Column(name="precio_euros", nullable = false)
    private BigDecimal precioEuros;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="empresa_nit", nullable = false)
    private Company empresa;

    @ManyToMany
    @JoinTable(
        name="producto_categoria",
        joinColumns = @JoinColumn(name = "producto_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Category> categorias;
}
