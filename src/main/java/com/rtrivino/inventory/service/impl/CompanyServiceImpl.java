package com.rtrivino.inventory.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rtrivino.inventory.entity.Company;
import com.rtrivino.inventory.exception.ElementNotFoundException;
import com.rtrivino.inventory.repository.CompanyRepository;
import com.rtrivino.inventory.service.CompanyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findById(String nit) {
        return companyRepository.findById(nit).orElseThrow(() -> new ElementNotFoundException("Compañia no encontrada"));

    }

    @Override
    public void delete(String nit) {
        companyRepository.deleteById(nit);
    }

    @Override
    public Company update(String nit, Company empresa) {
        Company companyDb = companyRepository.findById(nit)
                .orElseThrow(() -> new ElementNotFoundException("Compañía no encontrada con id: " + nit));

        companyDb.setNombre(empresa.getNombre());
        companyDb.setDireccion(empresa.getDireccion());;
        companyDb.setTelefono(empresa.getTelefono());
        
        return companyRepository.save(companyDb);
    } 
}
