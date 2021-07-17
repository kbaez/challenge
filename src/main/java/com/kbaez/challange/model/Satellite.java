package com.kbaez.challange.model;

import java.util.Objects;

import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class Satellite {
	
	public static final String KENOBI = "KENOBI";
	public static final String SKYWALKER = "SKYWALKER";
	public static final String SATO = "SATO";

	@PositiveOrZero(message = "distance can't be smaller than the 0")
	private Float distance;

	private String name;

	private String[] message;
	
	private SatelliteLocation location;
	
	public Satellite(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Satellite satellite = (Satellite) o;
		return name.equals(satellite.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getMessage() {
		return message;
	}

	public void setMessage(String[] message) {
		this.message = message;
	}

	public SatelliteLocation getLocation() {
		return location;
	}

	public void setLocation(SatelliteLocation location) {
		this.location = location;
	}	
}
