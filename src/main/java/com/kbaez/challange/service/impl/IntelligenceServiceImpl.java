package com.kbaez.challange.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.exception.NotFoundException;
import com.kbaez.challange.model.Satellite;
import com.kbaez.challange.model.SatelliteLocation;
import com.kbaez.challange.service.IntelligenceService;
import com.kbaez.challange.utils.LocationUtil;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

@Service
public class IntelligenceServiceImpl implements IntelligenceService {

	public static List<Satellite> satellites;

	@Autowired
	private LocationUtil locationUtil;
	
	private static final String ESPACIO = " ";

	@Override
	public String getMessage(List<String[]> messages) throws NotFoundException {

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
				throw new NotFoundException(
						"Error. No se ha podido decodificar el mensaje porque una parte del mensaje llego vacia a todos los satelites");
			}
		}

		return completedMessage.toString();
	}

	@Override
	public float[] getLocation(float[] distances) throws ConflictException {
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

//	@Override
//	public void getSatellites() {
//		// levantar desde un archivo
//		Satellite satelliteKenobi = new Satellite(Satellite.KENOBI);
//		Satellite satelliteSkywalker = new Satellite(Satellite.SKYWALKER);
//		Satellite satelliteSato = new Satellite(Satellite.SATO);
//
//		SatelliteLocation satelliteLocationKenobi = new SatelliteLocation(-500, -200);
//		SatelliteLocation satelliteLocationSkywalker = new SatelliteLocation(100, -100);
//		SatelliteLocation satelliteLocationSato = new SatelliteLocation(500, 100);
//
//		satelliteKenobi.setLocation(satelliteLocationKenobi);
//		satelliteSkywalker.setLocation(satelliteLocationSkywalker);
//		satelliteSato.setLocation(satelliteLocationSato);
//
//		satellites = (Arrays.asList(satelliteKenobi, satelliteSkywalker, satelliteSato));
//	}
	
	private boolean isLastPartOfMessage(int messageLength, int i) {
		return i == messageLength-1;
	}
}
