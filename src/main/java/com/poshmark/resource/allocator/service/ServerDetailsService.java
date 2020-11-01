package com.poshmark.resource.allocator.service;

import java.util.List;

import com.poshmark.resource.allocator.model.ServerCPUDetails;
import com.poshmark.resource.allocator.model.Servers;

public interface ServerDetailsService {

	List<Servers> getServerDetails();
	
	ServerCPUDetails populateCPUType();
}
