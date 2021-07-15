package com.kbaez.challange.service;

import java.util.List;

import com.kbaez.challange.exception.PositionNotDeterminedException;
import com.kbaez.challange.model.Vector;

public interface IntelligenceService {
	
	Vector getLocation(float distance) throws PositionNotDeterminedException;

	String getMessage(List<String> listMessages);
}
