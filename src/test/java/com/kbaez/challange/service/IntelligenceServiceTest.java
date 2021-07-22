package com.kbaez.challange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.scheduling.annotation.Scheduled;

import com.kbaez.challange.dto.PositionMessageResponse;
import com.kbaez.challange.dto.request.TopSecretRequest;
import com.kbaez.challange.dto.request.TopSecretSplitRequest;
import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.exception.NoContentException;
import com.kbaez.challange.exception.NotFoundException;
import com.kbaez.challange.model.Location;
import com.kbaez.challange.model.Satellite;
import com.kbaez.challange.repository.SatelliteRepository;
import com.kbaez.challange.service.impl.IntelligenceServiceImpl;
import com.kbaez.challange.service.impl.SatelliteServiceImpl;
import com.kbaez.challange.utils.LocationUtil;

public class IntelligenceServiceTest {

	private SatelliteService satelliteService;
//	private LocationUtil locationUtil;
	private SatelliteRepository satelliteRepository;
	private IntelligenceService intelligenceService;
	private static final String SATO = "sato";

	@BeforeEach
	void setUp() {
		satelliteRepository = Mockito.mock(SatelliteRepository.class);
		satelliteService = new SatelliteServiceImpl(satelliteRepository);
		intelligenceService = new IntelligenceServiceImpl(satelliteService);
	}

	@Test
	void testGetLocationAndMessageThenReturnLocationAndMessage() throws NotFoundException, ConflictException {
		PositionMessageResponse positionMessageResponse = buildPositionMessageResponse(-100.0f, 75.5f,
				"este es un mensaje secreto");
		Mockito.when(intelligenceService.getLocationAndMessage(Mockito.any(TopSecretRequest.class)))
				.thenReturn(positionMessageResponse);
		PositionMessageResponse response = intelligenceService.getLocationAndMessage(buildNewTopSecretRequest());
		assertEquals(positionMessageResponse, response);
	}

	@Test
	void testSaveSatelliteThenReturnSatellite() throws NotFoundException, ConflictException, NoContentException {
		TopSecretSplitRequest topSecretSplitRequest = buildTopSecretSplitRequest();
		PositionMessageResponse positionMessageResponse = buildPositionMessageResponse(500f, 100f, "este un");
		Mockito.when(intelligenceService.saveSatellite(SATO, Mockito.any(TopSecretSplitRequest.class)))
				.thenReturn(positionMessageResponse);
		PositionMessageResponse response = intelligenceService.saveSatellite(SATO, topSecretSplitRequest);
		assertEquals(positionMessageResponse, response);
	}

	private TopSecretSplitRequest buildTopSecretSplitRequest() {
		TopSecretSplitRequest topSecretSplitRequest = new TopSecretSplitRequest();
		topSecretSplitRequest.setDistance(120.5f);
		topSecretSplitRequest.setMessage(new String[] { "este", "", "", "mensaje", "" });
		return topSecretSplitRequest;
	}

	private PositionMessageResponse buildPositionMessageResponse(float x, float y, String msj) {
		PositionMessageResponse positionMessageResponse = new PositionMessageResponse();
		positionMessageResponse.setLocation(new Location(x, y));
		positionMessageResponse.setMessage(msj);
		return positionMessageResponse;
	}

	private TopSecretRequest buildNewTopSecretRequest() {
		TopSecretRequest request = new TopSecretRequest();
		request.setSatellites(getListSatellite());
		return request;
	}

	private List<Satellite> getListSatellite() {
		List<Satellite> list = new ArrayList<>();
		Satellite s1 = new Satellite();
		s1.setDistance(100.0f);
		s1.setLocation(new Location(-500, -200));
		s1.setMessage(new String[] { "este", "", "", "mensaje", "" });
		s1.setName("kenobi");

		Satellite s2 = new Satellite();
		s2.setDistance(115.5f);
		s2.setLocation(new Location(100, -100));
		s2.setMessage(new String[] { "", "es", "", "", "secreto" });
		s2.setName("skywalker");

		Satellite s3 = new Satellite();
		s3.setDistance(142.7f);
		s3.setLocation(new Location(500, 100));
		s3.setMessage(new String[] { "este", "", "un", "", "" });
		s3.setName("sato");

		list.add(s1);
		list.add(s2);
		list.add(s3);
		return list;
	}
}
