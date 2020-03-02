package com.example.rmmservices.services.commands;

import com.example.rmmservices.exceptions.DeviceNotFoundException;
import com.example.rmmservices.repositories.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class DeleteDeviceCommandService implements CommandService<Long> {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeleteDeviceCommandService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Void handle(Long deviceId) {

        this.deviceRepository.findById(deviceId)
                .orElseThrow(DeviceNotFoundException::new);

        this.deviceRepository.deleteById(deviceId);
        return null;
    }
}
