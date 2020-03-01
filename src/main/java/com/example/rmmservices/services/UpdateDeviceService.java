package com.example.rmmservices.services;

import com.example.rmmservices.exceptions.DeviceNotFoundException;
import com.example.rmmservices.models.Device;
import com.example.rmmservices.repositories.DeviceRepository;
import com.example.rmmservices.services.dtos.UpdateDeviceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UpdateDeviceService implements CommandService<UpdateDeviceDTO> {

    private final DeviceRepository deviceRepository;

    @Autowired
    public UpdateDeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Void execute(UpdateDeviceDTO updateDeviceDTO) {

        log.debug("updating device: {}", updateDeviceDTO);

        final Device currentDevice = this.deviceRepository.findById(updateDeviceDTO.getDeviceId())
                .orElseThrow(DeviceNotFoundException::new);

        currentDevice.setSystemName(updateDeviceDTO.getSystemName());

        this.deviceRepository.save(currentDevice);

        return null;
    }
}
