package com.example.rmmservices.services.queries;

import com.example.rmmservices.exceptions.CustomerNotFoundException;
import com.example.rmmservices.repositories.DeviceRepository;
import com.example.rmmservices.services.queries.dtos.DeviceDTO;
import com.example.rmmservices.services.queries.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceQueryService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceQueryService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    public List<DeviceDTO> findByCustomerId(Long id) {
        return DeviceMapper.toDTO(this.deviceRepository.findByCustomer_Id(id));
    }

    public DeviceDTO findById(UUID uuid) {
        return DeviceMapper.toDTO(this.deviceRepository.findById(uuid)
                .orElseThrow(CustomerNotFoundException::new));
    }
}
