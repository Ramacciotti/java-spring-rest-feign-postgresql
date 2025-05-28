package com.ramacciotti.repository;

import com.ramacciotti.model.Geo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GeoRepository extends JpaRepository<Geo, UUID> {
}
