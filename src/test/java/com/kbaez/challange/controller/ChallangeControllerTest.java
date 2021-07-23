package com.kbaez.challange.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.kbaez.challange.dto.PositionMessageResponse;
import com.kbaez.challange.dto.request.TopSecretRequest;
import com.kbaez.challange.dto.request.TopSecretSplitRequest;
import com.kbaez.challange.model.Location;
import com.kbaez.challange.model.Satellite;
import com.kbaez.challange.service.IntelligenceService;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ChallengeController.class)
@ActiveProfiles("test")
public class ChallangeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IntelligenceService intelligenceService;

	@Autowired
	private ObjectMapper objectMapper;
	
	private static final String KENOBI = "kenobi";
	private static final String SKYWALKER = "skywalker";
	private static final String SATO = "sato";

	@BeforeEach
	void setUp() {
	}

	@Test
	void testGetLocationAndMessageReturnsLocationAndMessage() throws Exception {

		PositionMessageResponse expectedPositionMessageResponse = buildPositionMessageResponse();

		TopSecretRequest topSecretRequest = buildTopSecretRequest();

		doReturn(expectedPositionMessageResponse).when(intelligenceService).getLocationAndMessage(topSecretRequest);
		
		mockMvc.perform(post("/topsecret")
				.content(objectMapper.writeValueAsString(topSecretRequest))
				.contentType("application/json"))
				.andExpect(status().isOk())
                .andExpect(jsonPath("$.position").value(expectedPositionMessageResponse.getLocation()))
                .andExpect(jsonPath("$.message").value(expectedPositionMessageResponse.getMessage()));

	}
	
	@Test
	void testGetLocationAndMessageSplitReturnsPositionMessageResponse() throws Exception {

		PositionMessageResponse expectedPositionMessageResponse = buildPositionMessageResponse();

		TopSecretSplitRequest topSecretSplitRequest = buildTopSecretSplitRequest();

		doReturn(expectedPositionMessageResponse).when(intelligenceService).saveSatellite(SATO, topSecretSplitRequest);
		
		mockMvc.perform(post("/topsecret_split/{satellite_name}", "sato")
				.content(objectMapper.writeValueAsString(topSecretSplitRequest))
				.contentType("application/json"))
				.andExpect(status().isOk());
	}

	private TopSecretSplitRequest buildTopSecretSplitRequest() {
		TopSecretSplitRequest topSecretSplitRequest = new TopSecretSplitRequest();
		topSecretSplitRequest.setDistance(100.0f);
		topSecretSplitRequest.setMessage(new String[] { "este", "", "", "mensaje", "" });
		return topSecretSplitRequest;
	}

	private PositionMessageResponse buildPositionMessageResponse() {

		PositionMessageResponse positionMessageResponse = new PositionMessageResponse();
		positionMessageResponse.setLocation(new Location(-58.31525f, -69.551414f));
		positionMessageResponse.setMessage("este es un mensaje secreto");

		return positionMessageResponse;
	}
	
	private TopSecretRequest buildTopSecretRequest() {
		TopSecretRequest topSecretRequest = new TopSecretRequest();
		topSecretRequest.setSatellites(getListSatellite());
		return topSecretRequest;
	}
	
	private List<Satellite> getListSatellite() {
		List<Satellite> list = new ArrayList<>();
		Satellite s1 = new Satellite();
		s1.setDistance(100.0f);
		s1.setX(-500f);
		s1.setY(-200f);
		s1.setMessage(new String[] { "este", "", "", "mensaje", "" });
		s1.setName(KENOBI);

		Satellite s2 = new Satellite();
		s2.setDistance(115.5f);
		s2.setX(100f);
		s2.setY(-100f);
		s2.setMessage(new String[] { "", "es", "", "", "secreto" });
		s2.setName(SKYWALKER);

		Satellite s3 = new Satellite();
		s3.setDistance(142.7f);
		s3.setX(500f);
		s3.setY(100f);
		s3.setMessage(new String[] { "este", "", "un", "", "" });
		s3.setName(SATO);

		list.add(s1);
		list.add(s2);
		list.add(s3);
		return list;
	}
}