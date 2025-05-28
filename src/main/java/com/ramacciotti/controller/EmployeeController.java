package com.ramacciotti.controller;

import com.ramacciotti.dto.EmployeeDTO;
import com.ramacciotti.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(path = "/employee")
    @Operation(description = "Save a list of employees from external API into the database.")
    public List<EmployeeDTO> saveEmployee() throws Exception {
        return employeeService.saveEmployee();
    }

    @GetMapping(path = "/employees")
    @Operation(description = "Returns a list of employees registered in the database")
    public List<EmployeeDTO> getEmployee() throws Exception {
        return employeeService.getEmployee();
    }

}
