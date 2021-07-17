package com.kbaez.challange.model;

public class SatelliteLocation extends Location{

	private Satellite satellite;
	
	public SatelliteLocation(float x, float y) {
		super(y, y);
	}

	public Satellite getSatelite() {
		return satellite;
	}

	public void setSatellite(Satellite satelite) {
		this.satellite = satelite;
	}
}
