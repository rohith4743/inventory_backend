package com.rohithkankipati.Inventory.exception;

import org.springframework.http.HttpStatus;

public class InventoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String errorCode;
	private final HttpStatus status;
	
	public InventoryException(String errorCode, HttpStatus status) {
		super();
		this.errorCode = errorCode;
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
}