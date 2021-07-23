package com.kbaez.challange.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "satellites")
@Accessors(chain = true)
public class Satellite {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@PositiveOrZero(message = "distance can't be smaller than the 0")
	private Float distance;

	private String name;

	private String[] message;

	private Float x;

	private Float y;

//	public Satellite() {
//	}
//	
//	public Satellite(String name) {
//		this.name = name;
//	}
//
//	public Float getDistance() {
//		return distance;
//	}
//
//	public void setDistance(Float distance) {
//		this.distance = distance;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String[] getMessage() {
//		return message;
//	}
//
//	public void setMessage(String[] message) {
//		this.message = message;
//	}
//
//	public Location getLocation() {
//		return location;
//	}
//
//	public void setLocation(Location location) {
//		this.location = location;
//	}

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
}
