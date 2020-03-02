package com.example.rmmservices.controllers;

import com.example.rmmservices.services.commands.CreateDeviceCommandService;
import com.example.rmmservices.services.commands.DeleteDeviceCommandService;
import com.example.rmmservices.services.commands.UpdateDeviceCommandService;
import com.example.rmmservices.services.commands.dtos.NewDeviceDTO;
import com.example.rmmservices.services.commands.dtos.UpdateDeviceDTO;
import com.example.rmmservices.services.queries.DeviceQueryService;
import com.example.rmmservices.services.queries.dtos.DeviceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("devices")
public class DeviceController {

    private final CreateDeviceCommandService createDeviceCommandService;

    private final UpdateDeviceCommandService updateDeviceCommandService;

    private final DeleteDeviceCommandService deleteDeviceCommandService;

    private final DeviceQueryService deviceQueryService;

    @Autowired
    public DeviceController(CreateDeviceCommandService createDeviceCommandService, UpdateDeviceCommandService updateDeviceCommandService, DeleteDeviceCommandService deleteDeviceCommandService, DeviceQueryService deviceQueryService) {
        this.createDeviceCommandService = createDeviceCommandService;
        this.updateDeviceCommandService = updateDeviceCommandService;
        this.deleteDeviceCommandService = deleteDeviceCommandService;
        this.deviceQueryService = deviceQueryService;
    }

    @PostMapping
    public ResponseEntity<Void> createDevice(@Valid @RequestBody NewDeviceDTO newDeviceDTO) {
        log.info("create device: {}", newDeviceDTO);
        return ResponseEntity.ok(this.createDeviceCommandService.handle(newDeviceDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDevice(@PathVariable Long id, @Valid @RequestBody UpdateDeviceDTO updateDeviceDTO) {
        log.info("Update device: {}, {}", id, updateDeviceDTO);

        updateDeviceDTO.setDeviceId(id);
        return ResponseEntity.ok(this.updateDeviceCommandService.handle(updateDeviceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        log.info("Delete device {}", id);

        return ResponseEntity.ok(this.deleteDeviceCommandService.handle(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> findById(@PathVariable Long id) {
        log.info("Getting device {}", id);
        return ResponseEntity.ok(this.deviceQueryService.findById(id));
    }
}
