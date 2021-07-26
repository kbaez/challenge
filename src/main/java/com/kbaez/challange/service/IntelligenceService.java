package com.kbaez.challange.service;

import com.kbaez.challange.dto.PositionMessageResponse;
import com.kbaez.challange.dto.request.TopSecretRequest;
import com.kbaez.challange.dto.request.TopSecretSplitRequest;

public interface IntelligenceService {

	PositionMessageResponse saveSatellite(String satelliteName, TopSecretSplitRequest request);
	
	PositionMessageResponse getLocationAndMessage(TopSecretRequest request);

	PositionMessageResponse getLocationAndMessageSplit();
}
