package com.ramacciotti.service.impl;

import com.ramacciotti.api.EmployeeApi;
import com.ramacciotti.dto.EmployeeDTO;
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

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeApi employeeApi;
    private final EmployeeRepository employeeRepository;
    private final AddressService addressService;
    private final CompanyService companyService;

    public EmployeeServiceImpl(EmployeeApi employeeApi, EmployeeRepository employeeRepository, AddressService addressService, CompanyService companyService) {
        this.employeeApi = employeeApi;
        this.employeeRepository = employeeRepository;
        this.addressService = addressService;
        this.companyService = companyService;
    }

    @Transactional
    public List<EmployeeDTO> saveEmployee() throws Exception {
        List<EmployeeDTO> employeeListDTO;

        try {
            log.info("Fetching data from external API...");
            employeeListDTO = employeeApi.fetchUsers();
            employeeListDTO.forEach(employee -> log.debug("Employee fetched from external API: {}", employee.getEmail()));
            saveToDatabase(employeeListDTO);
            log.info("Employees successfully saved on database!");
        } catch (FeignException error) {
            log.error("Could not fetch data from external API: {}", error.getMessage());
            throw new Exception("could_not_fetch_data_from_external_api");
        }

        return employeeListDTO;
    }

    private void saveToDatabase(List<EmployeeDTO> employeeListDTO) {
        log.info("Saving fetched employees into database...");

        for (EmployeeDTO employeeDTO : employeeListDTO) {

            Employee employee = new Employee()
                    .withEmail(employeeDTO.getEmail())
                    .withName(employeeDTO.getName())
                    .withPhone(employeeDTO.getPhone())
                    .withUsername(employeeDTO.getUsername())
                    .withWebsite(employeeDTO.getWebsite());

            employee = employeeRepository.save(employee);
            addressService.save(employeeDTO.getAddress(), employee);
            companyService.save(employeeDTO.getCompany(), employee);

        }

    }


    public List<EmployeeDTO> getEmployee() throws Exception {
        List<EmployeeDTO> employeeDTOList;

        try {
            log.info("Fetching employees from database...");
            List<Employee> employeeList = employeeRepository.findAll();
            ModelMapper modelMapper = new ModelMapper();
            employeeDTOList = Arrays.asList(modelMapper.map(employeeList, EmployeeDTO[].class));
            employeeDTOList.forEach(employee -> log.debug("Employee fetched from database: {}", employee.getEmail()));
            log.info("Employees successfully fetched!");
        } catch (Exception error) {
            log.error("Ops! Could not fetch employees from database: {}", error.getMessage());
            throw new Exception("could_not_fetch_data_from_database");
        }

        return employeeDTOList;
    }

}
