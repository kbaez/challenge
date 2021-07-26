package com.kbaez.challange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kbaez.challange.dto.PositionMessageResponse;
import com.kbaez.challange.dto.SatelliteDTO;
import com.kbaez.challange.dto.request.TopSecretRequest;
import com.kbaez.challange.dto.request.TopSecretSplitRequest;
import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.exception.SatelliteNotFoundException;
import com.kbaez.challange.model.Location;
import com.kbaez.challange.model.Satellite;
import com.kbaez.challange.service.impl.IntelligenceServiceImpl;
import com.kbaez.challange.utils.LocationUtil;

public class IntelligenceServiceTest {

	private SatelliteService satelliteService;
	private LocationUtil locationUtil;
	private IntelligenceService intelligenceService;
	private static final String KENOBI = "kenobi";
	private static final String SKYWALKER = "skywalker";
	private static final String SATO = "sato";

	@BeforeEach
	void setUp() {
		satelliteService = Mockito.mock(SatelliteService.class);
		locationUtil = Mockito.mock(LocationUtil.class);
		intelligenceService = new IntelligenceServiceImpl(satelliteService, locationUtil);
	}

	@Test
	void testGetLocationAndMessageThenReturnLocationAndMessage() {
		TopSecretRequest topSecretRequest = buildNewTopSecretRequest();
		PositionMessageResponse positionMessageResponse = buildPositionMessageResponse(-58.31525f, -69.551414f,
				"este es un mensaje secreto");
		Mockito.doNothing().when(locationUtil).validateSatelites(Mockito.anyList());
		Mockito.doNothing().when(locationUtil).validateDistances(Mockito.any());

		PositionMessageResponse response = intelligenceService.getLocationAndMessage(topSecretRequest);
		assertEquals(positionMessageResponse, response);
	}
	
	@Test
	void testGetLocationAndMessageThenThrowsConflictExceptionBySatellite() {
		TopSecretRequest topSecretRequest = buildNewTopSecretRequestWrong();
		
		doThrow(ConflictException.class).when(locationUtil).validateSatelitesDTO(Mockito.anyList());
		
		assertThrows(ConflictException.class, () -> {intelligenceService.getLocationAndMessage(topSecretRequest);});
  
	}
	
	@Test
	void testGetLocationAndMessageThenThrowsConflictExceptionByDistance() {
		TopSecretRequest topSecretRequest = buildNewTopSecretRequestWrong();
		
		doThrow(ConflictException.class).when(locationUtil).validateDistances(Mockito.any());
		
		assertThrows(ConflictException.class, () -> {intelligenceService.getLocationAndMessage(topSecretRequest);});
  
	}

	@Test
	void testSaveSatelliteThenReturnSatellite() {
		TopSecretSplitRequest topSecretSplitRequest = buildTopSecretSplitRequest();
		PositionMessageResponse positionMessageResponse = buildPositionMessageResponse(500f, 10f, "este, , , mensaje, ");
		Satellite satellite = buildSatellite();
		
		Mockito.when(satelliteService.getSatelliteByName(Mockito.anyString())).thenReturn(satellite);
		Mockito.when(satelliteService.saveOrUpdateSatellite(Mockito.any(Satellite.class))).thenReturn(satellite);
		
		PositionMessageResponse response = intelligenceService.saveSatellite(KENOBI, topSecretSplitRequest);
		assertEquals(positionMessageResponse, response);
	}
	
	@Test
	void testSaveSatelliteThenReturnSatelliteThrowsNotFound() {
		TopSecretSplitRequest topSecretSplitRequest = buildTopSecretSplitRequest();
		
		doThrow(SatelliteNotFoundException.class).when(satelliteService).getSatelliteByName(Mockito.anyString());
		
		assertThrows(SatelliteNotFoundException.class, () -> {intelligenceService.saveSatellite(KENOBI,topSecretSplitRequest);});
  
	}

	@Test
	void testGetLocationAndMessageSplitThenReturnLocationAndMessage() {
		PositionMessageResponse positionMessageResponse = buildPositionMessageResponse(-58.31525f, -69.551414f,
				"este es un mensaje secreto");
		List<Satellite> listSatellites = getListSatellite();
		Mockito.doNothing().when(locationUtil).validateSatelites(Mockito.anyList());
		Mockito.doNothing().when(locationUtil).validateDistances(Mockito.any());
		
		Mockito.when(satelliteService.getAllSatellites()).thenReturn(listSatellites);

		PositionMessageResponse response = intelligenceService.getLocationAndMessageSplit();
		assertEquals(positionMessageResponse, response);
	}

	@Test
	void testGetLocationAndMessageSplitThenThrowsConflictExceptionBySatellite() {

		doThrow(ConflictException.class).when(locationUtil).validateSatelites(Mockito.anyList());

		assertThrows(ConflictException.class, () -> {
			intelligenceService.getLocationAndMessageSplit();
		});

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
		request.setSatellites(getListSatelliteDTO());
		return request;
	}

	private TopSecretRequest buildNewTopSecretRequestWrong() {
		TopSecretRequest request = new TopSecretRequest();
		request.setSatellites(getListSatelliteDTOWrong());
		return request;
	}

	private List<Satellite> getListSatellite() {
		List<Satellite> list = new ArrayList<>();
		Satellite s1 = new Satellite();
		s1.setDistance(100.0f);
		s1.setX(-500f);
		s1.setY(-200f);
		s1.setMessage("este, , ,mensaje, ");
		s1.setName(KENOBI);

		Satellite s2 = new Satellite();
		s2.setDistance(115.5f);
		s2.setX(100f);
		s2.setY(-100f);
		s2.setMessage(" ,es, , ,secreto");
		s2.setName(SKYWALKER);

		Satellite s3 = new Satellite();
		s3.setDistance(142.7f);
		s3.setX(500f);
		s3.setY(100f);
		s3.setMessage("este, ,un, , ");
		s3.setName(SATO);

		list.add(s1);
		list.add(s2);
		list.add(s3);
		return list;
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

	private List<SatelliteDTO> getListSatelliteDTOWrong() {
		List<SatelliteDTO> list = new ArrayList<>();
		SatelliteDTO s1 = new SatelliteDTO();
		s1.setDistance(100.0f);
		s1.setMessage(new String[] { "este", "", "", "mensaje", "" });
		s1.setName(KENOBI);
		list.add(s1);
		return list;
	}

	private Satellite buildSatellite() {
		Satellite s1 = new Satellite();
		s1.setDistance(100.0f);
		s1.setMessage("este, , ,mensaje, ");
		s1.setName(KENOBI);
		s1.setX(500f);
		s1.setY(10f);
		return s1;
	}
}
