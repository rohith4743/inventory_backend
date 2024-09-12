package com.rohithkankipati.Inventory.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	
	private HttpStatus status;
	private String message;
	private LocalDateTime timestamp;
	
	public ErrorResponse(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
	    return "{"
	            + "\"status\":" + status.value() + ","
	            + "\"error\":\"" + status.getReasonPhrase() + "\","
	            + "\"message\":\"" + message.replace("\"", "\\\"") + "\","
	            + "\"timestamp\":\"" + timestamp.toString() + "\""
	            + "}";
	}

}

