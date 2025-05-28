package com.ramacciotti.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import java.util.*;
import java.util.UUID;

import com.ramacciotti.dto.AddressDTO;
import com.ramacciotti.dto.CompanyDTO;
import com.ramacciotti.dto.EmployeeDTO;
import com.ramacciotti.exception.*;
import com.ramacciotti.service.EmployeeService;
import com.ramacciotti.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    private EmployeeController employeeController;

    private EmployeeDTO sampleEmployeeDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeController = new EmployeeController(employeeService);
        sampleEmployeeDTO = mockEmployee("Teste");
    }

    @Test
    @DisplayName("Should return OK with list of employees when fetchAndSaveEmployeesFromApi succeeds")
    public void testSaveEmployees_Success() {
        List<EmployeeDTO> employees = List.of(sampleEmployeeDTO);
        when(employeeService.fetchAndSaveEmployeesFromApi()).thenReturn(employees);

        ResponseEntity<List<EmployeeDTO>> response = employeeController.saveEmployees();

        assertEquals(OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
        verify(employeeService).fetchAndSaveEmployeesFromApi();
    }

    @Test
    @DisplayName("Should return BAD_GATEWAY when ExternalApiException is thrown in saveEmployees")
    public void testSaveEmployees_ExternalApiException() {
        when(employeeService.fetchAndSaveEmployeesFromApi()).thenThrow(new ExternalApiException("API error"));

        ResponseEntity<List<EmployeeDTO>> response = employeeController.saveEmployees();

        assertEquals(BAD_GATEWAY, response.getStatusCode());
        assertNull(response.getBody());
        verify(employeeService).fetchAndSaveEmployeesFromApi();
    }

    @Test
    @DisplayName("Should return INTERNAL_SERVER_ERROR when unexpected exception occurs in saveEmployees")
    public void testSaveEmployees_UnexpectedException() {
        when(employeeService.fetchAndSaveEmployeesFromApi()).thenThrow(new RuntimeException("Unexpected"));

        ResponseEntity<List<EmployeeDTO>> response = employeeController.saveEmployees();

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(employeeService).fetchAndSaveEmployeesFromApi();
    }

    @Test
    @DisplayName("Should return OK with list of employees when getEmployees succeeds")
    public void testGetEmployees_Success() {
        List<EmployeeDTO> employees = List.of(sampleEmployeeDTO);
        when(employeeService.getEmployees()).thenReturn(employees);

        ResponseEntity<List<EmployeeDTO>> response = employeeController.getEmployees();

        assertEquals(OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
        verify(employeeService).getEmployees();
    }

    @Test
    @DisplayName("Should return INTERNAL_SERVER_ERROR when DatabaseAccessException occurs in getEmployees")
    public void testGetEmployees_DatabaseAccessException() {
        when(employeeService.getEmployees()).thenThrow(new DatabaseAccessException("DB error"));

        ResponseEntity<List<EmployeeDTO>> response = employeeController.getEmployees();

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(employeeService).getEmployees();
    }

    @Test
    @DisplayName("Should return OK with employee when getEmployeeByUuid succeeds")
    public void testGetEmployeeByUuid_Success() {
        UUID uuid = UUID.randomUUID();
        when(employeeService.getEmployeeByUuid(uuid)).thenReturn(sampleEmployeeDTO);

        ResponseEntity<EmployeeDTO> response = employeeController.getEmployeeByUuid(uuid);

        assertEquals(OK, response.getStatusCode());
        assertEquals(sampleEmployeeDTO, response.getBody());
        verify(employeeService).getEmployeeByUuid(uuid);
    }

    @Test
    @DisplayName("Should return NOT_FOUND when EmployeeNotFoundException occurs in getEmployeeByUuid")
    public void testGetEmployeeByUuid_NotFound() {
        UUID uuid = UUID.randomUUID();
        when(employeeService.getEmployeeByUuid(uuid)).thenThrow(new EmployeeNotFoundException("Not found"));

        ResponseEntity<EmployeeDTO> response = employeeController.getEmployeeByUuid(uuid);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(employeeService).getEmployeeByUuid(uuid);
    }

    @Test
    @DisplayName("Should return OK with updated employee when updateEmployee succeeds")
    public void testUpdateEmployee_Success() {
        UUID uuid = UUID.randomUUID();
        when(employeeService.updateEmployee(uuid, sampleEmployeeDTO)).thenReturn(sampleEmployeeDTO);

        ResponseEntity<EmployeeDTO> response = employeeController.updateEmployee(uuid, sampleEmployeeDTO);

        assertEquals(OK, response.getStatusCode());
        assertEquals(sampleEmployeeDTO, response.getBody());
        verify(employeeService).updateEmployee(uuid, sampleEmployeeDTO);
    }

    @Test
    @DisplayName("Should return BAD_REQUEST when UpdateEmployeeException occurs in updateEmployee")
    public void testUpdateEmployee_UpdateEmployeeException() {
        UUID uuid = UUID.randomUUID();
        when(employeeService.updateEmployee(uuid, sampleEmployeeDTO)).thenThrow(new UpdateEmployeeException("Update error"));

        ResponseEntity<EmployeeDTO> response = employeeController.updateEmployee(uuid, sampleEmployeeDTO);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(employeeService).updateEmployee(uuid, sampleEmployeeDTO);
    }

    @Test
    @DisplayName("Should return NOT_FOUND when EmployeeNotFoundException occurs in updateEmployee")
    public void testUpdateEmployee_EmployeeNotFoundException() {
        UUID uuid = UUID.randomUUID();
        when(employeeService.updateEmployee(uuid, sampleEmployeeDTO)).thenThrow(new EmployeeNotFoundException("Not found"));

        ResponseEntity<EmployeeDTO> response = employeeController.updateEmployee(uuid, sampleEmployeeDTO);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(employeeService).updateEmployee(uuid, sampleEmployeeDTO);
    }

    @Test
    @DisplayName("Should return NO_CONTENT when deleteEmployee succeeds")
    public void testDeleteEmployee_Success() {
        UUID uuid = UUID.randomUUID();
        doNothing().when(employeeService).deleteEmployee(uuid);

        ResponseEntity<Void> response = employeeController.deleteEmployee(uuid);

        assertEquals(NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(employeeService).deleteEmployee(uuid);
    }

    @Test
    @DisplayName("Should return BAD_REQUEST when DeleteEmployeeException occurs in deleteEmployee")
    public void testDeleteEmployee_DeleteEmployeeException() {
        UUID uuid = UUID.randomUUID();
        doThrow(new DeleteEmployeeException("Delete error")).when(employeeService).deleteEmployee(uuid);

        ResponseEntity<Void> response = employeeController.deleteEmployee(uuid);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(employeeService).deleteEmployee(uuid);
    }

    @Test
    @DisplayName("Should return NOT_FOUND when EmployeeNotFoundException occurs in deleteEmployee")
    public void testDeleteEmployee_EmployeeNotFoundException() {
        UUID uuid = UUID.randomUUID();
        doThrow(new EmployeeNotFoundException("Not found")).when(employeeService).deleteEmployee(uuid);

        ResponseEntity<Void> response = employeeController.deleteEmployee(uuid);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(employeeService).deleteEmployee(uuid);
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