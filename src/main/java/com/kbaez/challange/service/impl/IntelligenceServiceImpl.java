package com.kbaez.challange.service.impl;

import java.util.List;

import com.kbaez.challange.exception.PositionNotDeterminedException;
import com.kbaez.challange.model.Vector;
import com.kbaez.challange.service.IntelligenceService;
import com.kbaez.challange.utils.CalculateHelper;

public class IntelligenceServiceImpl implements IntelligenceService{

	@Override
	public String getMessage(List<String> listMessages) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getLocation(float distance) throws PositionNotDeterminedException{
//		return CalculateHelper.determinePosition(config.getSatellites().get(SATELLITE_0),
//		        config.getSatellites().get(SATELLITE_1), config.getSatellites().get(SATELLITE_2), distances,
//		        config.getToleranceError());
		return null;
	}

}
