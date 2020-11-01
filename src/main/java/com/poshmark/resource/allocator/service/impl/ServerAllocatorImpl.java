package com.poshmark.resource.allocator.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.poshmark.resource.allocator.model.AllocatorRequest;
import com.poshmark.resource.allocator.model.AllocatorResponse;
import com.poshmark.resource.allocator.model.AllottedServer;
import com.poshmark.resource.allocator.model.ServerDetail;
import com.poshmark.resource.allocator.model.Servers;
import com.poshmark.resource.allocator.service.AllocatorService;
import com.poshmark.resource.allocator.service.ServerDetailsService;

@Component
public class ServerAllocatorImpl implements AllocatorService {

	@Autowired
	ServerDetailsService serverDetailsService;

	@Autowired
	List<Servers> servers;

	@Autowired
	List<AllocatorResponse> response;

	@Override
	public List<AllocatorResponse> getCosts(AllocatorRequest request) {
		this.response = new ArrayList<AllocatorResponse>();
		this.servers = serverDetailsService.getServerDetails();

		Double cost = request.getCost();
		int hours = request.getHours();
		int cpu = request.getCpu();

		if (null != cost && 0 != hours && 0 != cpu) {
			List<AllocatorResponse> unFIlteredServers = fetchDetails(cost, cpu, hours);
			unFIlteredServers.sort(Comparator.comparingDouble(AllocatorResponse::getCost));
			List<AllocatorResponse> filtered = unFIlteredServers.stream().filter(response -> response.getCost() <= cost)
					.collect(Collectors.toList());
			return filtered;
		}

		if (null == cost) {
			List<AllocatorResponse> unFIlteredServers = fetchDetails(0.0d, cpu, hours);
			unFIlteredServers.sort(Comparator.comparingDouble(AllocatorResponse::getCost));
			return unFIlteredServers;
		}

		if (0 == cpu) {
			List<AllocatorResponse> unFIlteredServers = fetchDetails(hours, cost);
			unFIlteredServers.sort(Comparator.comparingDouble(AllocatorResponse::getCost));
			return unFIlteredServers;
		}

		if (null != cost) {
			List<AllocatorResponse> unFIlteredServers = fetchDetails(hours, cost);
			unFIlteredServers.sort(Comparator.comparingDouble(AllocatorResponse::getCost));
			return unFIlteredServers;
		}

		else
			return new ArrayList<AllocatorResponse>();

	}

	private List<AllocatorResponse> fetchDetails(Double cost, int cpu, int time) {

		ServerDetail cpuHours = serverDetailsService.populateCPUType().getDetails();

		int hours10x = cpuHours.get_10xlarge().intValue();
		int hours8x = cpuHours.get_8xlarge().intValue();
		int hours4x = cpuHours.get_4xlarge().intValue();
		int hours2x = cpuHours.get_2xlarge().intValue();
		int hoursx = cpuHours.getXlarge().intValue();
		int hours0x = cpuHours.getLarge().intValue();

		List<AllocatorResponse> allresponses = new ArrayList<AllocatorResponse>();

		for (Servers server : this.servers) {

			ServerDetail details = server.getDetails();
			AllocatorResponse allocatorResponse = new AllocatorResponse();
			AllottedServer allottedServer = new AllottedServer();
			allocatorResponse.setZoneName(server.getZoneName());
			int cpuAlloted = 0;
			Double incurredCost = 0.0d;

			int remainingCPU = cpu - cpuAlloted;

			if (null != details.get_10xlarge() && remainingCPU >= hours10x) {
				int _10xLarge = Math.floorDiv(remainingCPU, hours10x);
				allottedServer.set_10xlarge(_10xLarge);
				cpuAlloted += hours10x * _10xLarge;
				remainingCPU = cpu - cpuAlloted;
				incurredCost += details.get_10xlarge() * _10xLarge;
			}
			if (null != details.get_8xlarge() && remainingCPU >= hours8x) {
				int _8xLarge = Math.floorDiv(remainingCPU, hours8x);
				cpuAlloted += hours8x * _8xLarge;
				allottedServer.set_8xlarge(_8xLarge);
				remainingCPU = cpu - cpuAlloted;
				incurredCost += details.get_8xlarge() * _8xLarge;
			}
			if (null != details.get_4xlarge() && remainingCPU >= hours4x) {
				int _4xLarge = Math.floorDiv(remainingCPU, hours4x);
				cpuAlloted += hours4x * _4xLarge;
				allottedServer.set_4xlarge(_4xLarge);
				remainingCPU = cpu - cpuAlloted;
				incurredCost += details.get_4xlarge() * _4xLarge;
			}
			if (null != details.get_2xlarge() && remainingCPU >= hours2x) {
				int _2xLarge = Math.floorDiv(remainingCPU, hours2x);
				cpuAlloted += hours2x * _2xLarge;
				allottedServer.set_2xlarge(_2xLarge);
				remainingCPU = cpu - cpuAlloted;
				incurredCost += details.get_2xlarge() * _2xLarge;
			}
			if (null != details.getXlarge() && remainingCPU >= hoursx) {
				int _1xLarge = Math.floorDiv(remainingCPU, hoursx);
				cpuAlloted += hoursx * _1xLarge;
				allottedServer.setXlarge(_1xLarge);
				remainingCPU = cpu - cpuAlloted;
				incurredCost += details.getXlarge() * _1xLarge;
			}
			if (null != details.getLarge() && remainingCPU >= hours0x) {
				int _0xLarge = Math.floorDiv(remainingCPU, hours0x);
				cpuAlloted += hours0x * _0xLarge;
				allottedServer.setXlarge(_0xLarge);
				remainingCPU = cpu - cpuAlloted;
				incurredCost += details.getLarge() * _0xLarge;
			}
			if (time != 0)
				incurredCost = incurredCost * time;
			allocatorResponse.setCost(incurredCost);
			allocatorResponse.setDetails(allottedServer);
			allresponses.add(allocatorResponse);
		}

		return allresponses;
	}

	private List<AllocatorResponse> fetchDetails(int hours, Double cost) {

		ServerDetail cpuHours = serverDetailsService.populateCPUType().getDetails();
		int hours10x = cpuHours.get_10xlarge().intValue();
		int hours8x = cpuHours.get_8xlarge().intValue();
		int hours4x = cpuHours.get_4xlarge().intValue();
		int hours2x = cpuHours.get_2xlarge().intValue();
		int hoursx = cpuHours.getXlarge().intValue();
		int hours0x = cpuHours.getLarge().intValue();

		List<AllocatorResponse> allresponses = new ArrayList<AllocatorResponse>();

		for (Servers server : this.servers) {

			ServerDetail details = server.getDetails();
			AllocatorResponse allocatorResponse = new AllocatorResponse();
			AllottedServer allottedServer = new AllottedServer();
			allocatorResponse.setZoneName(server.getZoneName());
			int cpuAlloted = 0;
			Double incurredCost = 0.0d;

			Double remainingCost = cost - incurredCost;

			if (null != details.get_10xlarge() && remainingCost >= (hours * details.get_10xlarge())) {
				int _10xLarge = (int) (remainingCost / (hours * details.get_10xlarge()));
				allottedServer.set_10xlarge(_10xLarge);
				cpuAlloted += hours10x * _10xLarge;
				incurredCost += details.get_10xlarge() * _10xLarge * hours;
				remainingCost = cost - incurredCost;
			}
			if (null != details.get_8xlarge() && remainingCost >= (hours * details.get_8xlarge())) {
				int _8xLarge = (int) (remainingCost / (hours * details.get_8xlarge()));
				allottedServer.set_8xlarge(_8xLarge);
				cpuAlloted += hours8x * _8xLarge;
				incurredCost += details.get_8xlarge() * _8xLarge * hours;
				remainingCost = cost - incurredCost;
			}
			if (null != details.get_4xlarge() && remainingCost >= (hours * details.get_4xlarge())) {
				int _4xLarge = (int) (remainingCost / (hours * details.get_4xlarge()));
				allottedServer.set_4xlarge(_4xLarge);
				cpuAlloted += hours4x * _4xLarge;
				incurredCost += details.get_4xlarge() * _4xLarge * hours;
				remainingCost = cost - incurredCost;
			}
			if (null != details.get_2xlarge() && remainingCost >= (hours * details.get_2xlarge())) {
				int _2xLarge = (int) (remainingCost / (hours * details.get_2xlarge()));
				allottedServer.set_2xlarge(_2xLarge);
				cpuAlloted += hours2x * _2xLarge;
				incurredCost += details.get_8xlarge() * _2xLarge * hours;
				remainingCost = cost - incurredCost;
			}
			if (null != details.getXlarge() && remainingCost >= (hours * details.getXlarge())) {
				int _xLarge = (int) (remainingCost / (hours * details.getXlarge()));
				allottedServer.setXlarge(_xLarge);
				cpuAlloted += hoursx * _xLarge;
				incurredCost += details.getXlarge() * _xLarge * hours;
				remainingCost = cost - incurredCost;
			}
			if (null != details.getLarge() && remainingCost >= (hours * details.getLarge())) {
				int _0xLarge = (int) (remainingCost / (hours * details.getLarge()));
				allottedServer.setLarge(_0xLarge);
				cpuAlloted += hours0x * _0xLarge;
				incurredCost += details.getLarge() * _0xLarge * hours;
				remainingCost = cost - incurredCost;
			}

			allocatorResponse.setDetails(allottedServer);
			allocatorResponse.setCost(incurredCost);

			allresponses.add(allocatorResponse);
		}

		return allresponses;
	}
}
