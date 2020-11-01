package com.poshmark.resource.allocator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ServerDetail {

	@JsonProperty("large")
	private Double large;
	@JsonProperty("xlarge")
	private Double xlarge;
	@JsonProperty("2xlarge")
	private Double _2xlarge;
	@JsonProperty("4xlarge")
	private Double _4xlarge;
	@JsonProperty("8xlarge")
	private Double _8xlarge;
	@JsonProperty("10xlarge")
	private Double _10xlarge;
}
