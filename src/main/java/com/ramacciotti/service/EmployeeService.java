package com.ramacciotti.service;

import com.ramacciotti.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> saveEmployee() throws Exception;

    List<EmployeeDTO> getEmployee() throws Exception;

}
