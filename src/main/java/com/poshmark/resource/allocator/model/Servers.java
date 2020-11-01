package com.poshmark.resource.allocator.model;

import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Component
public class Servers {

	private String zoneName;
	private ServerDetail details;
	
	public Servers(String zoneName, ServerDetail details) {
		super();
		this.zoneName = zoneName;
		this.details = details;
	}
	
	
}
