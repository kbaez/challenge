package com.kbaez.challange.service;

import com.kbaez.challange.dto.PositionMessageResponse;
import com.kbaez.challange.dto.request.TopSecretRequest;
import com.kbaez.challange.dto.request.TopSecretSplitRequest;
import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.exception.NoContentException;
import com.kbaez.challange.exception.NotFoundException;

public interface IntelligenceService {

	PositionMessageResponse saveSatellite(String satelliteName, TopSecretSplitRequest request)
			throws NoContentException;
	
	PositionMessageResponse getLocationAndMessage(TopSecretRequest request) throws NotFoundException, ConflictException;

//	PositionMessageResponse getLocationAndMessageSplit();
}
