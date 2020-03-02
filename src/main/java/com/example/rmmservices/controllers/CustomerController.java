package com.example.rmmservices.controllers;

import com.example.rmmservices.services.queries.DeviceQueryService;
import com.example.rmmservices.services.queries.ServiceQueryService;
import com.example.rmmservices.services.queries.dtos.DeviceDTO;
import com.example.rmmservices.services.queries.dtos.ServiceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("customers")
public class CustomerController {

    private final ServiceQueryService serviceQueryService;

    private final DeviceQueryService deviceQueryService;

    @Autowired
    public CustomerController(ServiceQueryService serviceQueryService, DeviceQueryService deviceQueryService) {
        this.serviceQueryService = serviceQueryService;
        this.deviceQueryService = deviceQueryService;
    }

    @GetMapping("/{customerId}/services")
    public ResponseEntity<List<ServiceDTO>> findServicesByCustomerId(@PathVariable Long customerId) {
        log.info("getting services {}", customerId);

        return ResponseEntity.ok(serviceQueryService.findByCustomerId(customerId));
    }

    @GetMapping("/{customerId}/devices")
    public ResponseEntity<List<DeviceDTO>> findDevicesByCustomerId(@PathVariable Long customerId) {
        log.info("getting services {}", customerId);

        return ResponseEntity.ok(deviceQueryService.findByCustomerId(customerId));
    }

}
