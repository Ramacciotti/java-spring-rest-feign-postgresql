package com.ramacciotti.service;

import com.ramacciotti.dto.CompanyDTO;
import com.ramacciotti.model.Employee;

public interface CompanyService {

    void save(CompanyDTO companyDTO, Employee employee);

}
