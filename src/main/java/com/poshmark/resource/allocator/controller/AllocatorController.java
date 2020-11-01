package com.poshmark.resource.allocator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poshmark.resource.allocator.model.AllocatorRequest;
import com.poshmark.resource.allocator.model.AllocatorResponse;
import com.poshmark.resource.allocator.service.AllocatorService;

@RestController
public class AllocatorController {

	@Autowired
	AllocatorService allocatorService;
	
	@PostMapping(value = "/getcosts")
	public List<AllocatorResponse> getServerDetails(@RequestBody AllocatorRequest request) {
		return allocatorService.getCosts(request);
	}
}
