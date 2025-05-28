package com.ramacciotti.service;

import com.ramacciotti.dto.EmployeeDTO;
import com.ramacciotti.exception.*;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    List<EmployeeDTO> fetchAndSaveEmployeesFromApi() throws ExternalApiException;

    List<EmployeeDTO> getEmployees() throws DatabaseAccessException;

    EmployeeDTO getEmployeeByUuid(UUID uuid) throws EmployeeNotFoundException;

    EmployeeDTO updateEmployee(UUID uuid, EmployeeDTO employeeDTO) throws UpdateEmployeeException;

    void deleteEmployee(UUID uuid) throws DeleteEmployeeException;

}
