package com.kbaez.challange.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbaez.challange.exception.NoContentException;
import com.kbaez.challange.model.Satellite;
import com.kbaez.challange.repository.SatelliteRepository;
import com.kbaez.challange.service.SatelliteService;

@Service
public class SatelliteServiceImpl implements SatelliteService{

	@Autowired
	private SatelliteRepository repository;

	public SatelliteServiceImpl(SatelliteRepository satelliteRepository) {
		this.repository = satelliteRepository;
	}

	@Override
	public Satellite getSatelliteByName(String name){
		//return repository.findByName(name).orElseThrow(() -> new NoContentException("No se ha encontrado un satelite con nombre: " + name));
		return repository.findByName(name);
	}

	@Override
	public Satellite saveOrUpdateSatellite(Satellite satellite) {
		return repository.save(satellite);
	}
}