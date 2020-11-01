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
@Component
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AllocatorResponse {

	@JsonProperty("region")
	private String zoneName;
	@JsonProperty("total_cost")
	private Double cost;
	@JsonProperty("servers")
	private AllottedServer details;
}
