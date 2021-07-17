package com.kbaez.challange.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.kbaez.challange.controller.ChallengeController;
import com.kbaez.challange.dto.PositionDTO;
import com.kbaez.challange.service.IntelligenceService;

public class ChallengeControllerImpl implements ChallengeController{
	
	@Autowired
	private IntelligenceService intelligenceService;

	@Override
	public ResponseEntity<PositionDTO> getLocation(float distance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessage(List<String> listMessages) {
		// TODO Auto-generated method stub
		return null;
	}

}
