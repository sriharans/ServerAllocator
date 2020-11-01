package com.poshmark.resource.allocator.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poshmark.resource.allocator.constant.PoshMarkConstant;
import com.poshmark.resource.allocator.exception.PoshmarkException;
import com.poshmark.resource.allocator.model.ServerCPUDetails;
import com.poshmark.resource.allocator.model.ServerDetail;
import com.poshmark.resource.allocator.model.ServerZone;
import com.poshmark.resource.allocator.model.Servers;
import com.poshmark.resource.allocator.service.ServerDetailsService;

@Service
public class ServerDetailsImpl implements ServerDetailsService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	List<Servers> servers;

	@Autowired
	ServerCPUDetails cpuhours;

	private Logger logger;

	public List<Servers> getServerDetails() {

		this.servers = new ArrayList<Servers>();

		try {
			ServerZone serverZone = readServerFromFile();
			addServersFromZone(serverZone);
		} catch (PoshmarkException e) {
			logger.error(PoshMarkConstant.EXCEPTION_OCCURED_WHILE_PARSING_SERVER_DETAILS_JSON);
		}

		return this.servers;
	}

	private ServerZone readServerFromFile() throws PoshmarkException {

		try {
			objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			File file = ResourceUtils.getFile("classpath:server.json");
			ServerZone serverZone = objectMapper.readValue(file, ServerZone.class);
			return serverZone;
		} catch (IOException e) {
			throw new PoshmarkException(PoshMarkConstant.EXCEPTION_OCCURED_WHILE_PARSING_SERVER_DETAILS_JSON, "1001");
		}
	}

	private void addServersFromZone(ServerZone serverZone) {

		if (null != serverZone) {

			ServerDetail usEast = serverZone.getUsEast();
			ServerDetail usWest = serverZone.getUsWest();
			ServerDetail asia = serverZone.getAsia();
			if (null != usEast) {
				Servers serverEast = new Servers("us-East", usEast);
				this.servers.add(serverEast);
			}
			if (null != usWest) {
				Servers serverWest = new Servers("us-West", usWest);
				this.servers.add(serverWest);

			}
			if (null != asia) {
				Servers serverAsia = new Servers("asia", asia);
				this.servers.add(serverAsia);
			}
		}

	}

	private void addServers(Servers server) {
		this.servers.add(server);
	}

	@Override
	public ServerCPUDetails populateCPUType() {
		ServerCPUDetails cpuhours = new ServerCPUDetails();
		try {
			objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			File file = ResourceUtils.getFile("classpath:cpuType.json");
			ServerDetail detail = objectMapper.readValue(file, ServerDetail.class);
			cpuhours.setDetails(detail);
			return cpuhours;

		} catch (Exception e) {
			logger.error(PoshMarkConstant.EXCEPTION_OCCURED_WHILE_PARSING_CPU_DETAILS_JSON);
		}
		return cpuhours;

	}
}
