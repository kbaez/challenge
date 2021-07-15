package com.kbaez.challange.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbaez.challange.dto.PositionDTO;

@RestController
@RequestMapping("/quasar")
public interface ChallengeController {

	@GetMapping("/location")
	ResponseEntity<PositionDTO> getLocation(float distance);
	
	@GetMapping("/message")
	String getMessage(List<String> listMessages);
}
