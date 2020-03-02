package com.example.rmmservices.services.commands;

import com.example.rmmservices.exceptions.DeviceNotFoundException;
import com.example.rmmservices.models.Device;
import com.example.rmmservices.repositories.DeviceRepository;
import com.example.rmmservices.services.commands.dtos.UpdateDeviceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UpdateDeviceCommandService implements CommandService<UpdateDeviceDTO> {

    private final DeviceRepository deviceRepository;

    @Autowired
    public UpdateDeviceCommandService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Void handle(UpdateDeviceDTO updateDeviceDTO) {

        log.debug("updating device: {}", updateDeviceDTO);

        final Device currentDevice = this.deviceRepository.findById(updateDeviceDTO.getDeviceId())
                .orElseThrow(DeviceNotFoundException::new);

        currentDevice.setSystemName(updateDeviceDTO.getSystemName());

        this.deviceRepository.save(currentDevice);

        return null;
    }
}
