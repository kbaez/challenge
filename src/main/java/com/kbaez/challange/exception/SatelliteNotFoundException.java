package com.kbaez.challange.exception;

public class SatelliteNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1778351035635962564L;

	public SatelliteNotFoundException(String message) {
		super(message);
	}
}
