package com.example.rmmservices.controllers;

import com.example.rmmservices.services.CreateDeviceService;
import com.example.rmmservices.services.DeleteDeviceService;
import com.example.rmmservices.services.UpdateDeviceService;
import com.example.rmmservices.services.dtos.NewDeviceDTO;
import com.example.rmmservices.services.dtos.UpdateDeviceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("devices")
public class DeviceController {

    private final CreateDeviceService createDeviceService;

    private final UpdateDeviceService updateDeviceService;

    private final DeleteDeviceService deleteDeviceService;

    @Autowired
    public DeviceController(CreateDeviceService createDeviceService, UpdateDeviceService updateDeviceService, DeleteDeviceService deleteDeviceService) {
        this.createDeviceService = createDeviceService;
        this.updateDeviceService = updateDeviceService;
        this.deleteDeviceService = deleteDeviceService;
    }

    @PostMapping
    public ResponseEntity<Void> createDevice(@Valid @RequestBody NewDeviceDTO newDeviceDTO) {
        log.info("create device: {}", newDeviceDTO);
        return ResponseEntity.ok(this.createDeviceService.handle(newDeviceDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDevice(@PathVariable UUID id, @Valid @RequestBody UpdateDeviceDTO updateDeviceDTO) {
        log.info("Update device: {}, {}", id, updateDeviceDTO);

        updateDeviceDTO.setDeviceId(id);
        return ResponseEntity.ok(this.updateDeviceService.handle(updateDeviceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable UUID id) {
        log.info("Delete device {}", id);

        return ResponseEntity.ok(this.deleteDeviceService.handle(id));
    }
}
