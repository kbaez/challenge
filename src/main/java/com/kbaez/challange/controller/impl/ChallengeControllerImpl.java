package com.kbaez.challange.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kbaez.challange.dto.response.PositionMessageResponse;
import com.kbaez.challange.model.Satellite;
import com.kbaez.challange.service.IntelligenceService;

@RestController
public class ChallengeControllerImpl{

	@Autowired
	private IntelligenceService intelligenceService;

	@PostMapping("/topsecret")
	public ResponseEntity<PositionMessageResponse> getLocationAndMessage(@RequestBody String request) {
		// Converting jsonData string into JSON object
		JSONObject jsnobject = new JSONObject(request);

		// Getting languages JSON array from the JSON object
		JSONArray jsonArray = jsnobject.getJSONArray("satellites");

		ArrayList<Satellite> listdata = new ArrayList<Satellite>();  
		
		if (jsonArray != null) {

			// Iterating JSON array
			for (int i = 0; i < jsonArray.length(); i++) {

				// Adding each element of JSON array into ArrayList
				listdata.add((Satellite) jsonArray.get(i));
			}
		}
		// Iterating ArrayList to print each element

		System.out.println("Each element of ArrayList");
		for (int i = 0; i < listdata.size(); i++) {
			// Printing each element of ArrayList
			System.out.println(listdata.get(i));
		}
		return null;

	}

	@PostMapping("/topsecret_split/{satellite_name}")
	public String getMessage(List<String> listMessages) {
		// TODO Auto-generated method stub
		return null;
	}
}
