package com.ramacciotti.external.api.service;

import com.ramacciotti.external.api.dto.CompanyDTO;
import com.ramacciotti.external.api.model.Company;
import com.ramacciotti.external.api.model.Person;
import com.ramacciotti.external.api.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void save(CompanyDTO companyDTO, Person person) {

        Company company = new Company()
                .withBs(companyDTO.getBs())
                .withName(companyDTO.getName())
                .withCatchPhrase(companyDTO.getCatchPhrase())
                .withPerson(person);

        companyRepository.save(company);

    }

}
