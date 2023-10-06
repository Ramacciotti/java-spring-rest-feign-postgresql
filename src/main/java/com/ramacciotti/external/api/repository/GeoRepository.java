package com.ramacciotti.external.api.repository;

import com.ramacciotti.external.api.model.Geo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GeoRepository extends JpaRepository<Geo, UUID> {
}
