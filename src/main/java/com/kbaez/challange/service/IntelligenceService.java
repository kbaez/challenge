package com.kbaez.challange.service;

import java.util.List;

import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.model.Location;

public interface IntelligenceService {

	String getMessage(List<String> listMessages);

	Location getLocation(float[] distances) throws ConflictException;
}
