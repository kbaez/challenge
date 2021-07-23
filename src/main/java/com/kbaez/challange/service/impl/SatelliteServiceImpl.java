package com.kbaez.challange.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbaez.challange.exception.NoContentException;
import com.kbaez.challange.exception.NotFoundException;
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
		return repository.findByName(name).orElseThrow(()
                -> new NotFoundException(String.format("The satellite with name %s does not exists", name)));
	}

	@Override
	public Satellite saveOrUpdateSatellite(Satellite satellite) {
		return repository.save(satellite);
	}
}
