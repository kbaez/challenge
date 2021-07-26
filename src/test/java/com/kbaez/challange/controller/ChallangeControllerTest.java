package com.kbaez.challange.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.kbaez.challange.dto.PositionMessageResponse;
import com.kbaez.challange.dto.SatelliteDTO;
import com.kbaez.challange.dto.request.TopSecretRequest;
import com.kbaez.challange.dto.request.TopSecretSplitRequest;
import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.exception.SatelliteNotFoundException;
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
	private static final String SATO_FAIL = "sado";

	@BeforeEach
	void setUp() {
	}

	@Test
	void testGetLocationAndMessageReturnsLocationAndMessage() throws Exception {

		PositionMessageResponse expectedPositionMessageResponse = buildPositionMessageResponse();

		TopSecretRequest topSecretRequest = buildTopSecretRequest();

		doReturn(expectedPositionMessageResponse).when(intelligenceService).getLocationAndMessage(Mockito.any(TopSecretRequest.class));
		
		mockMvc.perform(post("/topsecret")
				.content(objectMapper.writeValueAsString(topSecretRequest))
				.contentType("application/json"))
				.andExpect(status().isOk())
                .andExpect(jsonPath("$.position").value(expectedPositionMessageResponse.getLocation()))
                .andExpect(jsonPath("$.message").value(expectedPositionMessageResponse.getMessage()));

	}
	
	@Test
	void testGetLocationAndMessageReturnsConflictException() throws Exception {

		TopSecretRequest topSecretRequest = buildTopSecretRequestWrong();
		
		ConflictException conflictException = new ConflictException(String.format("Error. Quantity of satellites is less than 3. It can't be done true-range multilateration for location."));

        doThrow(conflictException).when(intelligenceService).getLocationAndMessage(topSecretRequest);

		mockMvc.perform(post("/topsecret")
				.content(objectMapper.writeValueAsString(topSecretRequest))
				.contentType("application/json"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Quantity error exception"))
				.andExpect(jsonPath("$.message").value(conflictException.getMessage()))
				.andExpect(jsonPath("$.status").value("400"));
	}

	@Test
	void testGetLocationAndMessageSplitReturnsPositionMessageResponse() throws Exception {

		PositionMessageResponse expectedPositionMessageResponse = buildPositionMessageResponse();

		TopSecretSplitRequest topSecretSplitRequest = buildTopSecretSplitRequest();

		doReturn(expectedPositionMessageResponse).when(intelligenceService).saveSatellite(SATO, topSecretSplitRequest);
		
		mockMvc.perform(post("/topsecret_split/{satellite_name}", SATO)
				.content(objectMapper.writeValueAsString(topSecretSplitRequest))
				.contentType("application/json"))
				.andExpect(status().isOk());
	}

    @Test
    void testGetLocationAndMessageSplitWithStatus404ReturnsSatelliteNotFoundException() throws Exception {
    	
    	TopSecretSplitRequest topSecretSplitRequest = buildTopSecretSplitRequest();

        SatelliteNotFoundException expectedException = new SatelliteNotFoundException(String.format("The satellite with name %s does not exists", SATO_FAIL));

        doThrow(expectedException).when(intelligenceService).saveSatellite(SATO_FAIL, topSecretSplitRequest);

		mockMvc.perform(post("/topsecret_split/{satellite_name}", SATO_FAIL)
				.content(objectMapper.writeValueAsString(topSecretSplitRequest))
				.contentType("application/json"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").value("Satellite not found exception"))
				.andExpect(jsonPath("$.message").value(expectedException.getMessage()))
				.andExpect(jsonPath("$.status").value("404"));
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
		topSecretRequest.setSatellites(getListSatelliteDTO());
		return topSecretRequest;
	}
	
	private List<SatelliteDTO> getListSatelliteDTO() {
		List<SatelliteDTO> list = new ArrayList<>();
		SatelliteDTO s1 = new SatelliteDTO();
		s1.setDistance(100.0f);
		s1.setMessage(new String[] { "este", "", "", "mensaje", "" });
		s1.setName(KENOBI);

		SatelliteDTO s2 = new SatelliteDTO();
		s2.setDistance(115.5f);
		s2.setMessage(new String[] { "", "es", "", "", "secreto" });
		s2.setName(SKYWALKER);

		SatelliteDTO s3 = new SatelliteDTO();
		s3.setDistance(142.7f);
		s3.setMessage(new String[] { "este", "", "un", "", "" });
		s3.setName(SATO);

		list.add(s1);
		list.add(s2);
		list.add(s3);
		return list;
	}

	private TopSecretRequest buildTopSecretRequestWrong() {
		TopSecretRequest topSecretRequest = new TopSecretRequest();
		
		List<SatelliteDTO> list = new ArrayList<>();
		SatelliteDTO s1 = new SatelliteDTO();
		s1.setDistance(100.0f);
		s1.setMessage(new String[] { "este", "", "", "mensaje", "" });
		s1.setName(KENOBI);

		topSecretRequest.setSatellites(list);
		return topSecretRequest;
	}
	
	private List<Satellite> getListSatellite() {
		List<Satellite> list = new ArrayList<>();
		Satellite s1 = new Satellite();
		s1.setDistance(100.0f);
		s1.setX(-500f);
		s1.setY(-200f);
		s1.setMessage("este,,,mensaje,");
		s1.setName(KENOBI);

		Satellite s2 = new Satellite();
		s2.setDistance(115.5f);
		s2.setX(100f);
		s2.setY(-100f);
		s2.setMessage(",es,,,secreto");
		s2.setName(SKYWALKER);

		Satellite s3 = new Satellite();
		s3.setDistance(142.7f);
		s3.setX(500f);
		s3.setY(100f);
		s3.setMessage("este,,un,,");
		s3.setName(SATO);

		list.add(s1);
		list.add(s2);
		list.add(s3);
		return list;
	}
}
