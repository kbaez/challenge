package com.kbaez.challange.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kbaez.challange.dto.PositionMessageResponse;

public interface ChallengeController {

	@PostMapping("/topsecret")
	ResponseEntity<PositionMessageResponse> getLocationAndMessage(@RequestBody String request);
	
	@PostMapping("/topsecret_split/{satellite_name}")
	String getMessage(List<String> listMessages);
}
