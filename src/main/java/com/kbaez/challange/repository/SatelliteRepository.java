package com.kbaez.challange.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kbaez.challange.model.Satellite;

@Repository
public interface SatelliteRepository extends CrudRepository<Satellite, Long> {

	Satellite save(Satellite satellite);

	Satellite findByName(String name);

}
