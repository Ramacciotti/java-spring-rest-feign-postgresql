package com.ramacciotti.controller;

import com.ramacciotti.dto.EmployeeDTO;
import com.ramacciotti.exception.*;
import com.ramacciotti.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
@Tag(name = "Employee Controller")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @Operation(description = "Fetch employees from external AðPI and save them in the database.")
    public ResponseEntity<List<EmployeeDTO>> saveEmployees() {
        try {
            List<EmployeeDTO> savedEmployees = employeeService.fetchAndSaveEmployeesFromApi();
            return ResponseEntity.ok(savedEmployees);
        } catch (ExternalApiException externalApiException) {
            log.error("Failed to fetch/save employees: {}", externalApiException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        } catch (Exception exception) {
            log.error("Unexpected error: {}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    @Operation(description = "Get all employees from the database.")
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        try {
            List<EmployeeDTO> employees = employeeService.getEmployees();
            return ResponseEntity.ok(employees);
        } catch (DatabaseAccessException databaseAccessException) {
            log.error("Failed to get employees: {}", databaseAccessException.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{uuid}")
    @Operation(description = "Get an employee by UUID.")
    public ResponseEntity<EmployeeDTO> getEmployeeByUuid(@PathVariable UUID uuid) {
        try {
            EmployeeDTO employeeDTO = employeeService.getEmployeeByUuid(uuid);
            return ResponseEntity.ok(employeeDTO);
        } catch (EmployeeNotFoundException employeeNotFoundException) {
            log.error("Employee not found: {}", employeeNotFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{uuid}")
    @Operation(description = "Update an existing employee by UUID.")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable UUID uuid,
            @RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO updatedEmployee = employeeService.updateEmployee(uuid, employeeDTO);
            return ResponseEntity.ok(updatedEmployee);
        } catch (UpdateEmployeeException updateEmployeeException) {
            log.error("Failed to update employee: {}", updateEmployeeException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EmployeeNotFoundException employeeNotFoundException) {
            log.error("Employee not found: {}", employeeNotFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{uuid}")
    @Operation(description = "Delete an employee by UUID.")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID uuid) {
        try {
            employeeService.deleteEmployee(uuid);
            return ResponseEntity.noContent().build();
        } catch (DeleteEmployeeException deleteEmployeeException) {
            log.error("Failed to delete employee: {}", deleteEmployeeException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EmployeeNotFoundException employeeNotFoundException) {
            log.error("Employee not found: {}", employeeNotFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}