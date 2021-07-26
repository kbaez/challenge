package com.kbaez.challange.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbaez.challange.exception.NoContentException;
import com.kbaez.challange.exception.SatelliteNotFoundException;
import com.kbaez.challange.model.Satellite;
import com.kbaez.challange.repository.SatelliteRepository;
import com.kbaez.challange.service.SatelliteService;

@Service
public class SatelliteServiceImpl implements SatelliteService{

	@Autowired
	private SatelliteRepository repository;

	@Autowired
	public SatelliteServiceImpl(SatelliteRepository satelliteRepository) {
		this.repository = satelliteRepository;
	}

	@Override
	public Satellite getSatelliteByName(String name){
		return repository.findByName(name).orElseThrow(()
                -> new SatelliteNotFoundException(String.format("The satellite with name %s does not exists", name)));
	}

	@Override
	public Satellite saveOrUpdateSatellite(Satellite satellite) {
		return repository.save(satellite);
	}

	@Override
	public List<Satellite> getAllSatellites() {
		return (List<Satellite>) repository.findAll();
	}
}
