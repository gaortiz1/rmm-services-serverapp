package com.example.rmmservices.controllers;

import com.example.rmmservices.services.commands.AddServiceCommandService;
import com.example.rmmservices.services.commands.DeleteServiceCommandService;
import com.example.rmmservices.services.commands.dtos.DeleteServiceDTO;
import com.example.rmmservices.services.commands.dtos.NewServiceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("services")
public class ServiceController {

    private final AddServiceCommandService addServiceCommandService;

    private final DeleteServiceCommandService deleteDeviceCommandService;

    @Autowired
    public ServiceController(AddServiceCommandService addServiceCommandService, DeleteServiceCommandService deleteDeviceCommandService) {
        this.addServiceCommandService = addServiceCommandService;
        this.deleteDeviceCommandService = deleteDeviceCommandService;
    }


    @PostMapping
    public ResponseEntity<Void> add(@Valid @RequestBody NewServiceDTO newServiceDTO) {
        log.info("add ser: {}", newServiceDTO);
        return ResponseEntity.ok(this.addServiceCommandService.handle(newServiceDTO));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteDevice(@Valid @RequestBody DeleteServiceDTO deleteServiceDTO) {
        log.info("Delete device {}", deleteServiceDTO);

        return ResponseEntity.ok(this.deleteDeviceCommandService.handle(deleteServiceDTO));
    }

}
