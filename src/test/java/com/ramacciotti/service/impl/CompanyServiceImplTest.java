package com.ramacciotti.service.impl;

import com.ramacciotti.dto.CompanyDTO;
import com.ramacciotti.model.Company;
import com.ramacciotti.model.Employee;
import com.ramacciotti.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private CompanyDTO companyDTO;
    private Employee employee;
    private Company company;

    @BeforeEach
    void setUp() {
        companyDTO = new CompanyDTO();
        companyDTO.setName("Company Name");
        companyDTO.setBs("Business Stuff");
        companyDTO.setCatchPhrase("Catchy phrase");

        employee = new Employee();
        employee.setId(UUID.randomUUID());

        company = new Company();
        company.setId(UUID.randomUUID());

        company = new Company()
                .withBs(companyDTO.getBs())
                .withName(companyDTO.getName())
                .withCatchPhrase(companyDTO.getCatchPhrase())
                .withEmployee(employee);
    }

    @Test
    @DisplayName("Should save company successfully")
    void testSave_Success() {
        when(companyRepository.save(any(Company.class))).thenReturn(company);
        assertDoesNotThrow(() -> companyService.save(companyDTO, employee));
        verify(companyRepository).save(any(Company.class));
    }

}
