package com.kbaez.challange.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kbaez.challange.exception.ConflictException;
import com.kbaez.challange.exception.SatelliteNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(value = { SatelliteNotFoundException.class })
	public ResponseEntity<ApiError> satelliteNotFoundException(SatelliteNotFoundException ex) {
		ApiError apiError = new ApiError("Satellite not found exception", ex.getMessage(),
				HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(apiError.getStatus()).body(apiError);
	}
	
	@ExceptionHandler(value = { ConflictException.class })
	public ResponseEntity<ApiError> conflictException(ConflictException ex) {
		ApiError apiError = new ApiError("Quantity error exception", ex.getMessage(),
				HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(apiError.getStatus()).body(apiError);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ApiError> handleUnknownException(HttpMessageNotReadableException ex) {
		ApiError apiError = new ApiError("Internal Error", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		return ResponseEntity.status(apiError.getStatus()).body(apiError);
	}
}
