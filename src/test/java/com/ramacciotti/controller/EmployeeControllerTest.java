package com.ramacciotti.controller;

import com.ramacciotti.dto.AddressDTO;
import com.ramacciotti.dto.CompanyDTO;
import com.ramacciotti.dto.EmployeeDTO;
import com.ramacciotti.service.impl.EmployeeServiceImpl;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeServiceImpl employeeServiceImpl;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Description("Testa o método saveEmployee do controller, verificando se a lista de funcionários é salva e retornada corretamente")
    void testSaveEmployee() throws Exception {

        EmployeeDTO emp1 = mockEmployee("Sebastian");
        EmployeeDTO emp2 = mockEmployee("Serena");
        List<EmployeeDTO> expectedList = Arrays.asList(emp1, emp2);

        when(employeeServiceImpl.saveEmployee()).thenReturn(expectedList);

        List<EmployeeDTO> actualList = employeeController.saveEmployee();

        assertNotNull(actualList);
        assertEquals(2, actualList.size());
        verify(employeeServiceImpl, times(1)).saveEmployee();

    }

    @Test
    @Description("Testa o método getEmployee do controller, garantindo que a lista de funcionários cadastrados seja retornada corretamente")
    void testGetEmployee() throws Exception {

        EmployeeDTO emp1 = mockEmployee("Maria");
        List<EmployeeDTO> expectedList = List.of(emp1);

        when(employeeServiceImpl.getEmployee()).thenReturn(expectedList);

        List<EmployeeDTO> actualList = employeeController.getEmployee();

        assertNotNull(actualList);
        assertEquals(1, actualList.size());
        verify(employeeServiceImpl, times(1)).getEmployee();

    }

    private EmployeeDTO mockEmployee(String suffix) {

        AddressDTO address = new AddressDTO();
        address.setStreet("Rua Exemplo " + suffix);
        address.setSuite("Apt " + suffix);
        address.setCity("Cidade " + suffix);
        address.setZipcode("12345-" + suffix);

        CompanyDTO company = new CompanyDTO();
        company.setName("Empresa " + suffix);
        company.setCatchPhrase("Inovação " + suffix);
        company.setBs("Business " + suffix);

        EmployeeDTO employee = new EmployeeDTO();
        employee.setName("Nome " + suffix);
        employee.setUsername("usuario" + suffix);
        employee.setEmail("email" + suffix + "@exemplo.com");
        employee.setPhone("1234-5678" + suffix);
        employee.setWebsite("www.site" + suffix + ".com");
        employee.setAddress(address);
        employee.setCompany(company);

        return employee;

    }

}