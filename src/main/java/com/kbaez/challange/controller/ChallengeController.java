package com.kbaez.challange.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kbaez.challange.dto.PositionMessageResponse;
import com.kbaez.challange.dto.request.TopSecretRequest;
import com.kbaez.challange.dto.request.TopSecretSplitRequest;
import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.exception.NoContentException;
import com.kbaez.challange.exception.SatelliteNotFoundException;
import com.kbaez.challange.service.IntelligenceService;

@RestController
@Validated
public class ChallengeController {

	@Autowired
	private IntelligenceService intelligenceService;

	@PostMapping("/topsecret")
	public ResponseEntity<PositionMessageResponse> getLocationAndMessage(@RequestBody TopSecretRequest request)
			throws ConflictException, SatelliteNotFoundException {
	
		PositionMessageResponse response = intelligenceService.getLocationAndMessage(request);

		return new ResponseEntity<PositionMessageResponse>(response, HttpStatus.OK);
	}

	@PostMapping("/topsecret_split/{satellite_name}")
	public ResponseEntity<PositionMessageResponse> getLocationAndMessageSplit(
			@PathVariable(value = "satellite_name") String satelliteName,
			@RequestBody TopSecretSplitRequest request){
			PositionMessageResponse response = intelligenceService.saveSatellite(satelliteName, request);
			return new ResponseEntity<>(response, HttpStatus.OK);
		
		
	}

	@GetMapping("/topsecret_split")
	public ResponseEntity<PositionMessageResponse> getLocationAndMessageSplit()
			throws ConflictException, SatelliteNotFoundException, NoContentException {
		
		PositionMessageResponse response = intelligenceService.getLocationAndMessageSplit();
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
