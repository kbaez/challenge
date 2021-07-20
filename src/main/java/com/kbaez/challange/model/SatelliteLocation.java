package com.kbaez.challange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SatelliteLocation extends Location{

	public SatelliteLocation(float x, float y) {
		super(x, y);
	}

	private Satellite satellite;
	
}
