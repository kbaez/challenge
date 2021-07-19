package com.kbaez.challange.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.model.Location;
import com.kbaez.challange.model.Satellite;
import com.kbaez.challange.model.SatelliteLocation;
import com.kbaez.challange.service.IntelligenceService;
import com.kbaez.challange.utils.LocationUtil;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

@Service
public class IntelligenceServiceImpl implements IntelligenceService {

//	private MainProperties config;

	public static List<Satellite> satellites;

	@Autowired
	private LocationUtil locationUtil;

	@Override
	public String getMessage(List<Satellite> satellites) {
		List<String[]> messages = satellites.stream().map(s -> s.getMessage()).collect(Collectors.toList());
		String completedMessage = "";
		int i = 0;

		for (String[] message : messages) {
			if (i < message.length) {
				if (!message[i].isEmpty()) {
					completedMessage = completedMessage + message[i];
				}
				i++;
			}
		}
		return completedMessage;
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
		float [] location = new float [2];
		location[0] = (float) result[0];
		location[1] = (float) result[1];
		return location;
	}

	@Override
	public void getSatellites() {
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

		satellites = (Arrays.asList(satelliteKenobi, satelliteSkywalker, satelliteSato));
	}
}
