package com.ramacciotti.service;

import com.ramacciotti.dto.AddressDTO;
import com.ramacciotti.model.Employee;

public interface AddressService {

    void save(AddressDTO addressDTO, Employee employee);

}
