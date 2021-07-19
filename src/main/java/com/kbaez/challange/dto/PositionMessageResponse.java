package com.kbaez.challange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbaez.challange.model.Location;

public class PositionMessageResponse {

		@JsonProperty("location")
		private Location location;
		
		@JsonProperty("message")
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}		
}
