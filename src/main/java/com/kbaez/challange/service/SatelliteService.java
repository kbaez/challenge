package com.kbaez.challange.service;

import java.util.List;

import com.kbaez.challange.model.Satellite;

public interface SatelliteService {
	List<Satellite> getAllSatellites();
	Satellite getSatelliteByName(String name);
	Satellite saveOrUpdateSatellite(Satellite satellite);
}
