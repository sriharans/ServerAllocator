package com.poshmark.resource.allocator.service;

import java.util.List;

import com.poshmark.resource.allocator.model.AllocatorRequest;
import com.poshmark.resource.allocator.model.AllocatorResponse;

public interface AllocatorService {

	List<AllocatorResponse> getCosts(AllocatorRequest request);
}
