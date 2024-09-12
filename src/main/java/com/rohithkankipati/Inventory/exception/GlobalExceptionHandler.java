package com.rohithkankipati.Inventory.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.rohithkankipati.Inventory.util.MessageUtil;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InventoryException.class)
    public ResponseEntity<ErrorResponse> handleSkedException(InventoryException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getStatus(), 
            MessageUtil.getMessage(ex.getErrorCode())
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
	
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgException(MethodArgumentNotValidException ex, WebRequest request) {
		
		List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

		String combinedErrorMessage = String.join(", ", errorMessages);
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST, 
            combinedErrorMessage
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
//		System.out.println(ex);
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST, 
            ex.getLocalizedMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}

