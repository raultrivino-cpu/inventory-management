package com.rtrivino.inventory.service;

import java.util.List;

import com.rtrivino.inventory.entity.Company;

public interface CompanyService {
    Company save(Company company);
    List<Company> findAll();
    Company findById(String nit);
    void delete(String nit);
    Company update(String nit, Company empresa);
}
