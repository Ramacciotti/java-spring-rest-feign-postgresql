package com.ramacciotti.service.impl;

import com.ramacciotti.dto.CompanyDTO;
import com.ramacciotti.model.Company;
import com.ramacciotti.model.Employee;
import com.ramacciotti.repository.CompanyRepository;
import com.ramacciotti.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void save(CompanyDTO companyDTO, Employee employee) {

        Company company = new Company()
                .withBs(companyDTO.getBs())
                .withName(companyDTO.getName())
                .withCatchPhrase(companyDTO.getCatchPhrase())
                .withEmployee(employee);

        companyRepository.save(company);

    }

}
