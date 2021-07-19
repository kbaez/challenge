package com.kbaez.challange.controller.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kbaez.challange.dto.PositionMessageResponse;
import com.kbaez.challange.dto.request.TopSecretRequest;
import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.model.Location;
import com.kbaez.challange.model.Satellite;
import com.kbaez.challange.service.IntelligenceService;

@RestController
public class ChallengeControllerImpl{

	@Autowired
	private IntelligenceService intelligenceService;

	@PostMapping("/topsecret")
	public ResponseEntity<PositionMessageResponse> getLocationAndMessage(@RequestBody TopSecretRequest request) throws ConflictException {
		int i = 0;
		float [] distances = new float[request.getSatellites().size()];
		
		for(Satellite s : request.getSatellites()) {
			distances[i] = s.getDistance();
			i++;
		}
	
		float [] points = intelligenceService.getLocation(distances);
		Location location = new Location(points[0], points[1]);
		String message = intelligenceService.getMessage(request.getSatellites());
		PositionMessageResponse response = new PositionMessageResponse();
		response.setLocation(location);
		response.setMessage(message);

		return new ResponseEntity<PositionMessageResponse>(response, HttpStatus.OK);

	}

	@PostMapping("/topsecret_split/{satellite_name}")
	public String getMessage(List<String> listMessages) {
		// TODO Auto-generated method stub
		return null;
	}
}
