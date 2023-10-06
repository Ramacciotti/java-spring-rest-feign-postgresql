package com.ramacciotti.external.api.service;

import com.ramacciotti.external.api.dto.GeoDTO;
import com.ramacciotti.external.api.model.Address;
import com.ramacciotti.external.api.model.Geo;
import com.ramacciotti.external.api.repository.GeoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GeoService {

    private final GeoRepository geoRepository;

    public GeoService(GeoRepository geoRepository) {
        this.geoRepository = geoRepository;
    }

    @Transactional
    public void save(GeoDTO geoDTO, Address address) {

        Geo geo = new Geo()
                .withLat(geoDTO.getLat())
                .withLng(geoDTO.getLng()).
                withAddress(address);

        geoRepository.save(geo);

    }

}
