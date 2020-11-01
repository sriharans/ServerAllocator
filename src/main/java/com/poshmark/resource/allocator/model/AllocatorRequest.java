package com.poshmark.resource.allocator.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode

public class AllocatorRequest {

	@JsonProperty("hours")
	private int hours;
	@JsonProperty("cpu")
	private int cpu;
	@JsonProperty("cost")
	private Double cost;
	public AllocatorRequest(int hours, int cpu, Double cost) {
		super();
		this.hours = hours;
		this.cpu = cpu;
		this.cost = cost;
	}
	
	
}
