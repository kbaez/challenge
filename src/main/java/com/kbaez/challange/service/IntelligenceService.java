package com.kbaez.challange.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.exception.NotFoundException;
import com.kbaez.challange.model.Location;
import com.kbaez.challange.model.Satellite;

public interface IntelligenceService {

	String getMessage(List<String[]> messages) throws NotFoundException;

	float[] getLocation(float[] distances) throws ConflictException;

//	void getSatellites();
}
