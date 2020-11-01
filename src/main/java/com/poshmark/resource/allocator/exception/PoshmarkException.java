package com.poshmark.resource.allocator.exception;

import org.springframework.stereotype.Service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PoshmarkException extends Exception {

	private String message;
	private String  errorCode;
	
	public PoshmarkException(String message, String  errorCode) {
		this.message = message;
		this.errorCode = errorCode;
	}
}
