package com.ramacciotti.service.impl;

import com.ramacciotti.dto.AddressDTO;
import com.ramacciotti.model.Address;
import com.ramacciotti.model.Employee;
import com.ramacciotti.repository.AddressRepository;
import com.ramacciotti.service.AddressService;
import com.ramacciotti.service.GeoService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final GeoService geoService;

    public AddressServiceImpl(AddressRepository addressRepository, GeoService geoService) {
        this.addressRepository = addressRepository;
        this.geoService = geoService;
    }

    @Transactional
    public void save(AddressDTO addressDTO, Employee employee) {

        Address address = new Address()
                .withCity(addressDTO.getCity())
                .withSuite(addressDTO.getSuite())
                .withStreet(addressDTO.getStreet())
                .withZipcode(addressDTO.getZipcode())
                .withEmployee(employee);

        address = addressRepository.save(address);

        geoService.save(addressDTO.getGeo(), address);

    }

}
