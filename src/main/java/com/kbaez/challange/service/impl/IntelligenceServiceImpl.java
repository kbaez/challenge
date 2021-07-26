package com.kbaez.challange.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbaez.challange.dto.PositionMessageResponse;
import com.kbaez.challange.dto.SatelliteDTO;
import com.kbaez.challange.dto.request.TopSecretRequest;
import com.kbaez.challange.dto.request.TopSecretSplitRequest;
import com.kbaez.challange.exception.SatelliteNotFoundException;
import com.kbaez.challange.model.Location;
import com.kbaez.challange.model.Satellite;
import com.kbaez.challange.service.IntelligenceService;
import com.kbaez.challange.service.SatelliteService;
import com.kbaez.challange.utils.LocationUtil;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

@Service
public class IntelligenceServiceImpl implements IntelligenceService {

	private static final String ESPACIO = " ";

	@Autowired
	private SatelliteService satelliteService;

	@Autowired
	private LocationUtil locationUtil;

	@Autowired
	public IntelligenceServiceImpl(SatelliteService satelliteService2, LocationUtil locationUtil2) {
		this.satelliteService = satelliteService2;
		this.locationUtil = locationUtil2;
	}

	@Override
	public PositionMessageResponse getLocationAndMessage(TopSecretRequest request) {
		locationUtil.validateSatelitesDTO(request.getSatellites());
		
		int i = 0;
		float[] distances = new float[request.getSatellites().size()];

		for (SatelliteDTO s : request.getSatellites()) {
			distances[i] = s.getDistance();
			i++;
		}

		locationUtil.validateDistances(distances);

		float[] points = getLocation(distances);
		Location location = new Location(points[0], points[1]);

		List<String[]> messages = request.getSatellites().stream().map(s -> s.getMessage()).collect(Collectors.toList());
		
		String message = getMessage(messages);
		PositionMessageResponse positionMessageResponse = new PositionMessageResponse();
		positionMessageResponse.setLocation(location);
		positionMessageResponse.setMessage(message);

		return positionMessageResponse;
	}

	@Override
	public PositionMessageResponse saveSatellite(String satelliteName, TopSecretSplitRequest request) {
		Satellite satellite = satelliteService.getSatelliteByName(satelliteName);
		satellite.setDistance(request.getDistance());
		satellite.setMessage(String.join(", ",request.getMessage()));

		Satellite satelliteSaved = satelliteService.saveOrUpdateSatellite(satellite);
		PositionMessageResponse positionMessageResponse = new PositionMessageResponse();
		positionMessageResponse.setLocation(new Location(satelliteSaved.getX(), satelliteSaved.getY()));
		positionMessageResponse.setMessage(satelliteSaved.getMessage());

		return positionMessageResponse;
	}

	@Override
	public PositionMessageResponse getLocationAndMessageSplit() {

		List<Satellite> listSatellites = satelliteService.getAllSatellites();
		
		locationUtil.validateSatelites(listSatellites);

		int i = 0;
		float[] distances = new float[listSatellites.size()];

		for (Satellite s : listSatellites) {
			distances[i] = s.getDistance();
			i++;
		}
		
		locationUtil.validateDistances(distances);

		float[] points = getLocation(distances);
		Location location = new Location(points[0], points[1]);

		List<String[]> messages = listSatellites.stream().map(s -> s.getMessage().split(",")).collect(Collectors.toList());
		String message = getMessage(messages);
		PositionMessageResponse positionMessageResponse = new PositionMessageResponse();
		positionMessageResponse.setLocation(location);
		positionMessageResponse.setMessage(message);

		return positionMessageResponse;

	}

	private String getMessage(List<String[]> messages) {

		String[] message1 = messages.get(0);
		String[] message2 = messages.get(1);
		String[] message3 = messages.get(2);

		int messageLength = message1.length;

		StringBuilder completedMessage = new StringBuilder();

		for (int i = 0; i < messageLength; i++) {

			if (message1[i] != null && message1[i].isEmpty() == false && message1[i].equals(ESPACIO) == false) {
				if (isLastPartOfMessage(messageLength, i)) {
					completedMessage.append(message1[i]);
				} else {
					completedMessage.append(message1[i]).append(ESPACIO);
				}
			} else if (message2[i] != null && message2[i].isEmpty() == false && message2[i].equals(ESPACIO) == false) {
				if (isLastPartOfMessage(messageLength, i)) {
					completedMessage.append(message2[i]);
				} else {
					completedMessage.append(message2[i]).append(ESPACIO);
				}
			} else if (message3[i] != null && message3[i].isEmpty() == false && message3[i].equals(ESPACIO) == false) {
				if (isLastPartOfMessage(messageLength, i)) {
					completedMessage.append(message3[i]);
				} else {
					completedMessage.append(message3[i]).append(ESPACIO);
				}
			} else {
				throw new SatelliteNotFoundException(
						"Error. It could't be built the message because some parts are emptys in all satellites.");
			}
		}

		return completedMessage.toString();
	}

	private float[] getLocation(float[] distances) {
		double[][] positions = new double[3][2];

		positions[0][0] = -500;
		positions[0][1] = -200;
		positions[1][0] = 100;
		positions[1][1] = -100;
		positions[2][0] = 500;
		positions[2][1] = 100;

		double[] output = new double[distances.length];
		for (int i = 0; i < distances.length; i++) {
			output[i] = distances[i];
		}

		TrilaterationFunction trilaterationFunction = new TrilaterationFunction(positions, output);
		NonLinearLeastSquaresSolver nSolver = new NonLinearLeastSquaresSolver(trilaterationFunction,
				new LevenbergMarquardtOptimizer());

		double[] result = nSolver.solve().getPoint().toArray();
		float[] location = new float[2];
		location[0] = (float) result[0];
		location[1] = (float) result[1];
		return location;
	}

	private boolean isLastPartOfMessage(int messageLength, int i) {
		return i == messageLength - 1;
	}

}
