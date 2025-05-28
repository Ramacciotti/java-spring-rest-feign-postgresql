package com.ramacciotti.service.impl;

import com.ramacciotti.api.EmployeeApi;
import com.ramacciotti.dto.EmployeeDTO;
import com.ramacciotti.exception.*;
import com.ramacciotti.model.Employee;
import com.ramacciotti.repository.EmployeeRepository;
import com.ramacciotti.service.AddressService;
import com.ramacciotti.service.CompanyService;
import com.ramacciotti.service.EmployeeService;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeApi employeeApi;
    private final EmployeeRepository employeeRepository;
    private final AddressService addressService;
    private final CompanyService companyService;
    private final ModelMapper modelMapper = new ModelMapper();

    public EmployeeServiceImpl(EmployeeApi employeeApi, EmployeeRepository employeeRepository, AddressService addressService, CompanyService companyService) {
        this.employeeApi = employeeApi;
        this.employeeRepository = employeeRepository;
        this.addressService = addressService;
        this.companyService = companyService;
    }

    @Override
    @Transactional
    public List<EmployeeDTO> fetchAndSaveEmployeesFromApi() throws ExternalApiException {
        try {
            log.info("Fetching data from external API...");
            List<EmployeeDTO> employees = employeeApi.fetchUsers();
            employees.forEach(employeeDTO -> log.debug("Fetched employee: {}", employeeDTO.getEmail()));
            saveEmployees(employees);
            log.info("Employees successfully saved to database!");
            return employees;
        } catch (FeignException feignException) {
            log.error("Failed to fetch data from external API", feignException);
            throw new ExternalApiException("Failed to fetch data from external API", feignException);
        } catch (Exception unexpectedException) {
            log.error("Unexpected error fetching data", unexpectedException);
            throw new ExternalApiException("Unexpected error fetching data", unexpectedException);
        }
    }

    private void saveEmployees(List<EmployeeDTO> employeeList) {
        log.info("Saving employees into database...");
        employeeList.forEach(this::saveEmployeeWithRelations);
    }

    private void saveEmployeeWithRelations(EmployeeDTO employeeDTO) {
        Employee employee = mapToEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        addressService.save(employeeDTO.getAddress(), employee);
        companyService.save(employeeDTO.getCompany(), employee);
    }

    private Employee mapToEntity(EmployeeDTO dto) {
        return new Employee()
                .withEmail(dto.getEmail())
                .withName(dto.getName())
                .withPhone(dto.getPhone())
                .withUsername(dto.getUsername())
                .withWebsite(dto.getWebsite());
    }


    @Override
    public List<EmployeeDTO> getEmployees() {
        try {
            log.info("Fetching employees from database...");
            List<Employee> employees = employeeRepository.findAll();
            List<EmployeeDTO> employeeDTOList = Arrays.asList(modelMapper.map(employees, EmployeeDTO[].class));
            employeeDTOList.forEach(employeeDTO -> log.debug("Employee fetched: {}", employeeDTO.getEmail()));
            log.info("Employees successfully fetched!");
            return employeeDTOList;
        } catch (Exception databaseAccessException) {
            log.error("Failed to fetch employees from database", databaseAccessException);
            throw new DatabaseAccessException("Failed to fetch employees from database", databaseAccessException);
        }
    }


    @Override
    public EmployeeDTO getEmployeeByUuid(UUID uuid) {
        try {
            log.info("Fetching employee with UUID: {}", uuid);
            Employee employee = findEmployeeByUuid(uuid);
            EmployeeDTO dto = modelMapper.map(employee, EmployeeDTO.class);
            log.debug("Employee fetched: {}", dto.getEmail());
            return dto;
        } catch (Exception employeeNotFoundException) {
            log.error("Failed to fetch employee with UUID {}", uuid, employeeNotFoundException);
            throw new EmployeeNotFoundException("Employee not found with UUID: " + uuid, employeeNotFoundException);
        }
    }


    @Override
    @Transactional
    public EmployeeDTO updateEmployee(UUID uuid, EmployeeDTO employeeDTO) {
        try {
            log.info("Updating employee with UUID: {}", uuid);
            Employee employee = findEmployeeByUuid(uuid);
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(employeeDTO, employee);
            Employee updatedEmployee = employeeRepository.save(employee);
            EmployeeDTO updatedDTO = modelMapper.map(updatedEmployee, EmployeeDTO.class);
            log.debug("Employee updated: {}", updatedDTO.getEmail());
            return updatedDTO;
        } catch (Exception updateEmployeeException) {
            log.error("Failed to update employee with UUID {}", uuid, updateEmployeeException);
            throw new UpdateEmployeeException("Could not update employee with UUID: " + uuid, updateEmployeeException);
        }
    }


    @Override
    @Transactional
    public void deleteEmployee(UUID uuid) {
        try {
            log.info("Deleting employee with UUID: {}", uuid);
            Employee employee = findEmployeeByUuid(uuid);
            employeeRepository.delete(employee);
            log.info("Employee with UUID {} successfully deleted", uuid);
        } catch (Exception deleteEmployeeException) {
            log.error("Failed to delete employee with UUID {}", uuid, deleteEmployeeException);
            throw new DeleteEmployeeException("Could not delete employee with UUID: " + uuid, deleteEmployeeException);
        }
    }

    private Employee findEmployeeByUuid(UUID uuid) {
        return employeeRepository.findById(uuid).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with UUID: " + uuid));
    }

}