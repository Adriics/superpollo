package com.personal.usersocial.presentation.config;

import org.springframework.http.HttpStatus;

public class PresentationException extends RuntimeException {

	private HttpStatus httpStatus;
	
	public PresentationException(String mensaje, HttpStatus httpStatus) {
		super(mensaje);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
}
