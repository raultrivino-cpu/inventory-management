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

import com.rtrivino.inventory.entity.Company;
import com.rtrivino.inventory.service.CompanyService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller responsible for company management operations.
 *
 * <p>
 * Companies are one of the core resources of the inventory system.
 * They are used to associate products with a specific business entity and
 * support the inventory consultation by company.
 * </p>
 *
 * <p>
 * This controller exposes standard CRUD endpoints for creating, retrieving,
 * updating and deleting companies. Access to these operations is restricted
 * through the Spring Security configuration according to the authenticated
 * user's role.
 * </p>
 */
@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public List<Company> findAll() {
        return companyService.findAll();
    }

    @GetMapping("/{nit}")
    public Company findById(@PathVariable String nit) {
        return companyService.findById(nit);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company save(@RequestBody Company empresa) {
        return companyService.save(empresa);
    }

    @DeleteMapping("/{nit}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String nit) {
        companyService.delete(nit);
    }

    @PutMapping("/{nit}")
    public Company update(@PathVariable String nit, @RequestBody Company empresa) {
        return companyService.update(nit, empresa);
    }
}
