package com.example.rmmservices.services;

import com.example.rmmservices.exceptions.DeviceNotFoundException;
import com.example.rmmservices.models.Device;
import com.example.rmmservices.repositories.DeviceRepository;
import com.example.rmmservices.services.dtos.UpdateDeviceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class DeleteDeviceService implements CommandService<UUID> {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeleteDeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Void execute(UUID deviceId) {

        this.deviceRepository.findById(deviceId)
                .orElseThrow(DeviceNotFoundException::new);

        this.deviceRepository.deleteById(deviceId);
        return null;
    }
}
