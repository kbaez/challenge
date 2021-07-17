package com.kbaez.challange.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbaez.challange.dto.LocationDTO;

public class PositionMessageResponse {

		@JsonProperty("location")
		private LocationDTO location;
		
		@JsonProperty("message")
		private String message;

		public LocationDTO getUbicacion() {
			return location;
		}

		public void setUbicacion(LocationDTO location) {
			this.location = location;
		}
}
