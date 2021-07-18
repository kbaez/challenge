package com.kbaez.challange.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.model.Location;
import com.kbaez.challange.model.Satellite;
import com.kbaez.challange.model.SatelliteLocation;
import com.kbaez.challange.service.IntelligenceService;
import com.kbaez.challange.utils.LocationUtil;

@Service
public class IntelligenceServiceImpl implements IntelligenceService {

//	private MainProperties config;
	
	@Autowired
	private LocationUtil locationUtil;

	@Override
	public String getMessage(List<Satellite> satellites) {
		List<String[]> messages = satellites.stream().map(s -> s.getMessage()).collect(Collectors.toList());
		String completedMessage = null;
		int i = 0;
		
		for(String [] message : messages) {
			if(i < message.length) {
				if(!message[i].isEmpty()) {
					completedMessage = completedMessage + message[i];
				}
				i++;
			}
		}
		return completedMessage;
	}

	@Override
	public Location getLocation(float[] distances) throws ConflictException {
		List<Satellite> satelites = getSatellites();
		float xPosition = getXPosition(satelites, distances);
		float yPosition = getYPosition(satelites, distances);

		return new Location(xPosition, yPosition);
	}
	
	private List<Satellite> getSatellites(){
		// levantar desde un archivo
		Satellite satelliteKenobi = new Satellite(Satellite.KENOBI);
		Satellite satelliteSkywalker = new Satellite(Satellite.SKYWALKER);
		Satellite satelliteSato = new Satellite(Satellite.SATO);

		SatelliteLocation satelliteLocationKenobi = new SatelliteLocation(-500, -200);
		SatelliteLocation satelliteLocationSkywalker = new SatelliteLocation(100, -100);
		SatelliteLocation satelliteLocationSato = new SatelliteLocation(500, 100);

		satelliteKenobi.setLocation(satelliteLocationKenobi);
		satelliteSkywalker.setLocation(satelliteLocationSkywalker);
		satelliteSato.setLocation(satelliteLocationSato);
		
		return new ArrayList<Satellite>(Arrays.asList(satelliteKenobi, satelliteSkywalker, satelliteSato));
	}
	
	private float getXPosition(List<Satellite> satelites, float[] distances) throws ConflictException {

		locationUtil.validateSatelites(satelites);
		locationUtil.validateDistances(distances);

		Satellite satelliteKenobi = getSatellite(satelites, Satellite.KENOBI);
		Satellite satelliteSkywalker = getSatellite(satelites, Satellite.SKYWALKER);
		Satellite satelliteSato = getSatellite(satelites, Satellite.SATO);

		float distanceSatelliteKenobi = distances[0];
		float distanceSatelliteSkywalker = distances[1];
		float distanceSatelliteSato = distances[2];

		float[] datosKenobi = {satelliteKenobi.getLocation().getX(), satelliteKenobi.getLocation().getY(), distanceSatelliteKenobi};
		float[] datosSkywalker = {satelliteSkywalker.getLocation().getX(), satelliteSkywalker.getLocation().getY(), distanceSatelliteSkywalker};
		float[] datosSato = {satelliteSato.getLocation().getX(), satelliteSato.getLocation().getY(), distanceSatelliteSato};

		return calculateXPositionUsingTrilateration(datosKenobi, datosSkywalker, datosSato);
	}

	private float getYPosition(List<Satellite> satelites, float[] distances) throws ConflictException {
		
		//refactorizar por favor

		locationUtil.validateSatelites(satelites);
		locationUtil.validateDistances(distances);

		Satellite satelliteKenobi = getSatellite(satelites, Satellite.KENOBI);
		Satellite satelliteSkywalker = getSatellite(satelites, Satellite.SKYWALKER);
		Satellite satelliteSato = getSatellite(satelites, Satellite.SATO);

		float distanceSatelliteKenobi = distances[0];
		float distanceSatelliteSkywalker = distances[1];
		float distanceSatelliteSato = distances[2];

		float[] datosKenobi = {satelliteKenobi.getLocation().getX(), satelliteKenobi.getLocation().getY(), distanceSatelliteKenobi};
		float[] datosSkywalker = {satelliteSkywalker.getLocation().getX(), satelliteSkywalker.getLocation().getY(), distanceSatelliteSkywalker};
		float[] datosSato = {satelliteSato.getLocation().getX(), satelliteSato.getLocation().getY(), distanceSatelliteSato};

		float x = calculateXPositionUsingTrilateration(datosKenobi, datosSkywalker, datosSato);
		
		return calculateYPositionUsingTrilateration(datosKenobi, datosSkywalker, x);
	}
	
	private Satellite getSatellite(List<Satellite> satelites, String name) {
		return satelites.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);
	}
	
	private float calculateXPositionUsingTrilateration(float[] datosSateliteKenobi, float[] datosSateliteSkywalker, float[] datosSateliteSato) {

		float x = (float) ( ( ( (Math.pow(datosSateliteKenobi[2], 2) - Math.pow(datosSateliteSkywalker[2], 2)) + (Math.pow(datosSateliteSkywalker[0],2) - Math.pow(datosSateliteKenobi[0],2)) + (Math.pow(datosSateliteSkywalker[1],2) - Math.pow(datosSateliteKenobi[1],2)) ) * 
				(2*datosSateliteSato[1]-2*datosSateliteSkywalker[1]) - ( (Math.pow(datosSateliteSkywalker[2],2)-Math.pow(datosSateliteSato[2],2)) + (Math.pow(datosSateliteSato[0],2)-Math.pow(datosSateliteSkywalker[0],2)) + (Math.pow(datosSateliteSato[1],2)-Math.pow(datosSateliteSkywalker[1],2)) ) * 
				(2*datosSateliteSkywalker[1]-2*datosSateliteKenobi[1]) ) / ( (2*datosSateliteSkywalker[0]-2*datosSateliteSato[0]) * (2*datosSateliteSkywalker[1]-2*datosSateliteKenobi[1]) - (2*datosSateliteKenobi[0]-2*datosSateliteSkywalker[0]) * (2*datosSateliteSato[1]-2*datosSateliteSkywalker[1] ) ) );
		return x;
	}

	private float calculateYPositionUsingTrilateration(float[] datosSateliteKenobi, float[] datosSateliteSkywalker, float x) {

		float y = (float) (( (Math.pow(datosSateliteKenobi[2],2) - Math.pow(datosSateliteSkywalker[2],2)) + (Math.pow(datosSateliteSkywalker[0],2)-Math.pow(datosSateliteKenobi[0],2)) + 
				(Math.pow(datosSateliteSkywalker[1],2) - Math.pow(datosSateliteKenobi[1],2)) + x*(2*datosSateliteKenobi[0]-2*datosSateliteSkywalker[0])) / (2*datosSateliteSkywalker[1]-2*datosSateliteKenobi[1]));
		return y;
	}
}
