package com.rtrivino.inventory.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    @Id
    @Column(name="nit")
    private String nit;

    @Column(name="nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name="direccion")
    private String direccion;
    
    @Column(name="telefono")
    private String telefono;

    @OneToMany(mappedBy = "empresa")
    @JsonIgnore
    private List<Product> products;
}
