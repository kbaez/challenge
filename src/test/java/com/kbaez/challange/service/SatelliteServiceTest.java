package com.kbaez.challange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kbaez.challange.model.Satellite;
import com.kbaez.challange.repository.SatelliteRepository;
import com.kbaez.challange.service.impl.SatelliteServiceImpl;

public class SatelliteServiceTest {

	private SatelliteService satelliteService;
	private SatelliteRepository satelliteRepository;

	@BeforeEach
	public void setUp() {
		satelliteRepository = Mockito.mock(SatelliteRepository.class);
		satelliteService = new SatelliteServiceImpl(satelliteRepository);
	}
	
	@Test
	public void testGetByNameThenReturnSameSatellite(){
		Satellite expectedSatelite = buildNewSatellite();
		Mockito.when(satelliteRepository.findByName(Mockito.any())).thenReturn(Optional.of(expectedSatelite));
		Satellite response = satelliteService.getSatelliteByName("sato");
		assertEquals(expectedSatelite, response);
	}

	@Test
	public void testSaveOrUpdateThenReturnSameSatellite() {
		Satellite expectedSatelite = buildNewSatellite();
		Mockito.when(satelliteRepository.save(Mockito.any(Satellite.class))).thenReturn(expectedSatelite);
		Satellite response = satelliteService.saveOrUpdateSatellite(expectedSatelite);
		assertEquals(expectedSatelite, response);
	}

	private Satellite buildNewSatellite() {
		Satellite expectedSatelite = new Satellite();
		expectedSatelite.setName("sato");
		expectedSatelite.setX(500f);
		expectedSatelite.setY(100f);
		expectedSatelite.setMessage("este,,un,,");
		expectedSatelite.setDistance(142.7f);
		return expectedSatelite;
	}
}
