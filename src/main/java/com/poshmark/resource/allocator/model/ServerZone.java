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
public class ServerZone {

	@JsonProperty("us-east")
	private ServerDetail usEast;
	@JsonProperty("us-west")
	private ServerDetail usWest;
	@JsonProperty("asia")
	private ServerDetail asia;

}
