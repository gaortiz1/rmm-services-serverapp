package com.example.rmmservices.controllers;

import com.example.rmmservices.services.queries.DeviceQueryService;
import com.example.rmmservices.services.queries.dtos.DeviceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("devices")
public class DeviceQueryController {

    private final DeviceQueryService deviceQueryService;

    public DeviceQueryController(DeviceQueryService deviceQueryService) {
        this.deviceQueryService = deviceQueryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> findById(@PathVariable Long id) {
        log.info("Getting device {}", id);
        return ResponseEntity.ok(this.deviceQueryService.findById(id));
    }
}
