package com.kbaez.challange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbaez.challange.model.Location;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PositionMessageResponse {

		@JsonProperty("position")
		private Location location;
		
		@JsonProperty("message")
		private String message;
}
