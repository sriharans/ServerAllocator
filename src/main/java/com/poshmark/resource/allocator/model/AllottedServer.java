package com.poshmark.resource.allocator.model;

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
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AllottedServer {

	@JsonProperty("large")
	private Integer large;
	@JsonProperty("xlarge")
	private Integer xlarge;
	@JsonProperty("2xlarge")
	private Integer _2xlarge;
	@JsonProperty("4xlarge")
	private Integer _4xlarge;
	@JsonProperty("8xlarge")
	private Integer _8xlarge;
	@JsonProperty("10xlarge")
	private Integer _10xlarge;

}
