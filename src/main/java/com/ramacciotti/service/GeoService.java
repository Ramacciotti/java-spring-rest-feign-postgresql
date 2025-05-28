package com.ramacciotti.service;

import com.ramacciotti.dto.GeoDTO;
import com.ramacciotti.model.Address;

public interface GeoService {

    void save(GeoDTO geoDTO, Address address);

}
