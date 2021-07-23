package com.kbaez.challange.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kbaez.challange.model.Satellite;

@Repository
public interface SatelliteRepository extends CrudRepository<Satellite, Long> {

	Satellite save(Satellite satellite);

	Optional<Satellite> findByName(String name);

}
