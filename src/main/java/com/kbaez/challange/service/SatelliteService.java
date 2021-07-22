package com.kbaez.challange.service;

import com.kbaez.challange.exception.NoContentException;
import com.kbaez.challange.model.Satellite;

public interface SatelliteService {
	Satellite getSatelliteByName(String name);
	Satellite saveOrUpdateSatellite(Satellite satellite);
}