package com.example.rmmservices.services.queries;

import com.example.rmmservices.models.Device;
import com.example.rmmservices.repositories.DeviceRepository;
import com.example.rmmservices.services.queries.dtos.DeviceDTO;
import com.example.rmmservices.services.queries.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceQueryService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceQueryService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceDTO findById(Long id) {
        return DeviceMapper.toDTO(this.deviceRepository.findById(id)
                .orElse(Device.builder().build()));
    }
}
