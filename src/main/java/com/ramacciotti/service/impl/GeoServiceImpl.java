package com.ramacciotti.service.impl;

import com.ramacciotti.dto.GeoDTO;
import com.ramacciotti.model.Address;
import com.ramacciotti.model.Geo;
import com.ramacciotti.repository.GeoRepository;
import com.ramacciotti.service.GeoService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GeoServiceImpl implements GeoService {

    private final GeoRepository geoRepository;

    public GeoServiceImpl(GeoRepository geoRepository) {
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
