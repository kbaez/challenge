package com.kbaez.challange.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import com.kbaez.challange.dto.SatelliteDTO;
import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.model.Satellite;

@Component
public class LocationUtil {

	public void validateDistances(float[] distances) {
		if (distances == null) {
			throw new ConflictException("Error. Quantity of distances is empty.");
		}
		if (distances.length < 3) {
			throw new ConflictException("Error. Quantity of distances is less than 3. It can't be done true-range multilateration for location.");
		}
	}

	public void validateSatelites(List<Satellite> satellites) {
		if (satellites == null) {
			throw new ConflictException("Error. Quantity of satellites is empty.");
		}
		if (satellites.size() < 3) {
			throw new ConflictException("Error. Quantity of satellites is less than 3. It can't be done true-range multilateration for location.");
		}
	}
	
	public void validateSatelitesDTO(List<SatelliteDTO> satellites) {
		if (satellites == null) {
			throw new ConflictException("Error. Quantity of satellites is empty.");
		}
		if (satellites.size() < 3) {
			throw new ConflictException("Error. Quantity of satellites is less than 3. It can't be done true-range multilateration for location.");
		}
	}

}
