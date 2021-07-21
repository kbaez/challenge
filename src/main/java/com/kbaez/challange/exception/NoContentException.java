package com.kbaez.challange.exception;

public class NoContentException extends Exception {

	private static final long serialVersionUID = 6940361269039890572L;

	public NoContentException() {
		super();
	}

	public NoContentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoContentException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoContentException(String message) {
		super(message);
	}

	public NoContentException(Throwable cause) {
		super(cause);
	}
}
