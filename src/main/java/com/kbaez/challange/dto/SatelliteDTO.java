package com.kbaez.challange.dto;

import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SatelliteDTO {
	
	@PositiveOrZero(message = "distance can't be smaller than the 0")
	private Float distance;

	private String name;

	private String[] message;

}
