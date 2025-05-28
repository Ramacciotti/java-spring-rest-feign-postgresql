package com.ramacciotti.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.ramacciotti.api.EmployeeApi;
import com.ramacciotti.dto.EmployeeDTO;
import com.ramacciotti.exception.*;
import com.ramacciotti.model.Employee;
import com.ramacciotti.repository.EmployeeRepository;
import com.ramacciotti.service.AddressService;
import com.ramacciotti.service.CompanyService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.modelmapper.ModelMapper;

public class EmployeeServiceImplTest {

    @Mock
    private EmployeeApi employeeApi;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeDTO employeeDTO;
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeServiceImpl(employeeApi, employeeRepository, addressService, companyService);

        employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail("test@example.com");
        employeeDTO.setName("John Doe");
        employeeDTO.setUsername("johndoe");
        employeeDTO.setPhone("123456789");
        employeeDTO.setWebsite("johndoe.com");

        employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setEmail("test@example.com");
        employee.setName("John Doe");
        employee.setUsername("johndoe");
        employee.setPhone("123456789");
        employee.setWebsite("johndoe.com");
    }

    @Test
    @DisplayName("Should fetch and save employees successfully from external API")
    void testFetchAndSaveEmployeesFromApi_Success() throws Exception {
        List<EmployeeDTO> employeeList = List.of(employeeDTO);
        when(employeeApi.fetchUsers()).thenReturn(employeeList);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        List<EmployeeDTO> result = employeeService.fetchAndSaveEmployeesFromApi();

        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).getEmail());
        verify(employeeApi).fetchUsers();
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    @DisplayName("Should throw ExternalApiException when FeignException occurs in fetchAndSaveEmployeesFromApi")
    void testFetchAndSaveEmployeesFromApi_FeignException() throws Exception {
        when(employeeApi.fetchUsers()).thenThrow(mock(feign.FeignException.class));

        assertThrows(ExternalApiException.class, () -> employeeService.fetchAndSaveEmployeesFromApi());

        verify(employeeApi).fetchUsers();
    }

    @Test
    @DisplayName("Should throw ExternalApiException when unexpected exception occurs in fetchAndSaveEmployeesFromApi")
    void testFetchAndSaveEmployeesFromApi_UnexpectedException() throws Exception {
        when(employeeApi.fetchUsers()).thenThrow(new RuntimeException("Unexpected"));

        assertThrows(ExternalApiException.class, () -> employeeService.fetchAndSaveEmployeesFromApi());

        verify(employeeApi).fetchUsers();
    }

    @Test
    @DisplayName("Should fetch all employees from database successfully")
    void testGetEmployees_Success() {
        List<Employee> employees = List.of(employee);
        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeDTO> result = employeeService.getEmployees();

        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).getEmail());
        verify(employeeRepository).findAll();
    }

    @Test
    @DisplayName("Should throw DatabaseAccessException when exception occurs in getEmployees")
    void testGetEmployees_DatabaseAccessException() {
        when(employeeRepository.findAll()).thenThrow(new RuntimeException("DB error"));

        assertThrows(DatabaseAccessException.class, () -> employeeService.getEmployees());

        verify(employeeRepository).findAll();
    }

    @Test
    @DisplayName("Should get employee by UUID successfully")
    void testGetEmployeeByUuid_Success() {
        UUID uuid = employee.getId();
        when(employeeRepository.findById(uuid)).thenReturn(Optional.of(employee));

        EmployeeDTO result = employeeService.getEmployeeByUuid(uuid);

        assertEquals("test@example.com", result.getEmail());
        verify(employeeRepository).findById(uuid);
    }

    @Test
    @DisplayName("Should throw EmployeeNotFoundException when employee not found by UUID")
    void testGetEmployeeByUuid_NotFound() {
        UUID uuid = UUID.randomUUID();
        when(employeeRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeByUuid(uuid));

        verify(employeeRepository).findById(uuid);
    }

    @Test
    @DisplayName("Should update employee successfully")
    void testUpdateEmployee_Success() {
        UUID uuid = employee.getId();
        when(employeeRepository.findById(uuid)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO updatedDTO = employeeService.updateEmployee(uuid, employeeDTO);

        assertEquals("test@example.com", updatedDTO.getEmail());
        verify(employeeRepository).findById(uuid);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    @DisplayName("Should throw UpdateEmployeeException when exception occurs during update")
    void testUpdateEmployee_Exception() {
        UUID uuid = UUID.randomUUID();
        when(employeeRepository.findById(uuid)).thenThrow(new RuntimeException("Error"));

        assertThrows(UpdateEmployeeException.class, () -> employeeService.updateEmployee(uuid, employeeDTO));

        verify(employeeRepository).findById(uuid);
    }

    @Test
    @DisplayName("Should delete employee successfully")
    void testDeleteEmployee_Success() {
        UUID uuid = employee.getId();
        when(employeeRepository.findById(uuid)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        assertDoesNotThrow(() -> employeeService.deleteEmployee(uuid));

        verify(employeeRepository).findById(uuid);
        verify(employeeRepository).delete(employee);
    }

    @Test
    @DisplayName("Should throw DeleteEmployeeException when exception occurs during delete")
    void testDeleteEmployee_Exception() {
        UUID uuid = UUID.randomUUID();
        when(employeeRepository.findById(uuid)).thenThrow(new RuntimeException("Error"));

        assertThrows(DeleteEmployeeException.class, () -> employeeService.deleteEmployee(uuid));

        verify(employeeRepository).findById(uuid);
    }

}