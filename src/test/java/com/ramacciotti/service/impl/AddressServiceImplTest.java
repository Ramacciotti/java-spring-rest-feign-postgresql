package com.ramacciotti.service.impl;

import com.ramacciotti.dto.AddressDTO;
import com.ramacciotti.dto.GeoDTO;
import com.ramacciotti.model.Address;
import com.ramacciotti.model.Employee;
import com.ramacciotti.repository.AddressRepository;
import com.ramacciotti.service.GeoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private GeoService geoService;

    @InjectMocks
    private AddressServiceImpl addressService;

    private AddressDTO addressDTO;
    private Employee employee;
    private Address address;

    @BeforeEach
    void setUp() {
        addressDTO = new AddressDTO();
        addressDTO.setCity("City");
        addressDTO.setSuite("Suite");
        addressDTO.setStreet("Street");
        addressDTO.setZipcode("12345");
        addressDTO.setGeo(new GeoDTO("1.0", "2.0"));

        employee = new Employee();
        employee.setId(UUID.randomUUID());

        address = new Address();
        address.setId(UUID.randomUUID());

        address = new Address()
                .withCity(addressDTO.getCity())
                .withSuite(addressDTO.getSuite())
                .withStreet(addressDTO.getStreet())
                .withZipcode(addressDTO.getZipcode())
                .withEmployee(employee);
    }

    @Test
    @DisplayName("Should save address and call geoService")
    void testSave_Success() {
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        assertDoesNotThrow(() -> addressService.save(addressDTO, employee));
        verify(addressRepository).save(any(Address.class));
        verify(geoService).save(eq(addressDTO.getGeo()), any(Address.class));
    }

}
