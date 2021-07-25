package com.kbaez.challange.exception;

public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = 9083256796346963270L;

	public ConflictException(String message) {
		super(message);
	}
}
