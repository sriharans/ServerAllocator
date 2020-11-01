package com.poshmark.resource.allocator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poshmark.resource.allocator.model.Servers;
import com.poshmark.resource.allocator.service.ServerDetailsService;

@RestController
public class ServerDetailsController {
	@Autowired
	ServerDetailsService serverDetailsService;

	@RequestMapping(value = "/getServerDetails", method = RequestMethod.GET)
	public List<Servers> getServerDetails() {
		return serverDetailsService.getServerDetails();
	}
}
