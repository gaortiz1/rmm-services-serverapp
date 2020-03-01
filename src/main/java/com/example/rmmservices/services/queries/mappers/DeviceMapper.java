package com.example.rmmservices.services.queries.mappers;

import com.example.rmmservices.models.Device;
import com.example.rmmservices.services.queries.dtos.DeviceDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DeviceMapper {

    private DeviceMapper() { }

    public static DeviceDTO toDTO(Device device) {
        return DeviceDTO.builder()
                .id(device.getId())
                .systemName(device.getSystemName())
                .build();
    }

    public static List<DeviceDTO> toDTO(List<Device> devices) {
        return devices.stream()
                .map(DeviceMapper::toDTO)
                .collect(Collectors.toList());
    }
}
