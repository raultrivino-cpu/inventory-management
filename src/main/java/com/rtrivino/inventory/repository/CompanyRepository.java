package com.rtrivino.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtrivino.inventory.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, String>{

}
