package com.ramacciotti.external.api.service;

import com.ramacciotti.external.api.dto.AddressDTO;
import com.ramacciotti.external.api.model.Address;
import com.ramacciotti.external.api.model.Person;
import com.ramacciotti.external.api.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    private final GeoService geoService;

    public AddressService(AddressRepository addressRepository, GeoService geoService) {
        this.addressRepository = addressRepository;
        this.geoService = geoService;
    }

    @Transactional
    public void save(AddressDTO addressDTO, Person person) {

        Address address = new Address()
                .withCity(addressDTO.getCity())
                .withSuite(addressDTO.getSuite())
                .withStreet(addressDTO.getStreet())
                .withZipcode(addressDTO.getZipcode())
                .withPerson(person);

        address = addressRepository.save(address);

        geoService.save(addressDTO.getGeo(), address);

    }

}
