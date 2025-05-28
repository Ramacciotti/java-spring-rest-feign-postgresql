package com.ramacciotti.service.impl;

import com.ramacciotti.dto.GeoDTO;
import com.ramacciotti.model.Address;
import com.ramacciotti.model.Geo;
import com.ramacciotti.repository.GeoRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeoServiceImplTest {

    @Mock
    private GeoRepository geoRepository;

    @InjectMocks
    private GeoServiceImpl geoService;

    private GeoDTO geoDTO;
    private Address address;
    private Geo geo;

    @BeforeEach
    void setUp() {
        geoDTO = new GeoDTO();
        geoDTO.setLat("1.0");
        geoDTO.setLng("2.0");

        address = new Address();
        address.setId(UUID.randomUUID());

        geo = new Geo();
        geo.setId(UUID.randomUUID());

        geo = new Geo()
                .withLat(geoDTO.getLat())
                .withLng(geoDTO.getLng())
                .withAddress(address);
    }

    @Test
    @DisplayName("Should save geo successfully")
    void testSave_Success() {
        when(geoRepository.save(any(Geo.class))).thenReturn(geo);
        assertDoesNotThrow(() -> geoService.save(geoDTO, address));
        verify(geoRepository).save(any(Geo.class));
    }

}
