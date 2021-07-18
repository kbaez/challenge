package com.kbaez.challange.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

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
