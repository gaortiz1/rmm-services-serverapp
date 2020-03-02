package com.example.rmmservices.services.queries.mappers;

import com.example.rmmservices.models.Service;
import com.example.rmmservices.services.queries.dtos.ServiceDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceMapper {

    private ServiceMapper() { }

    public static ServiceDTO toDTO(Service service) {
        return ServiceDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .build();
    }

    public static List<ServiceDTO> toDTO(List<Service> devices) {
        return devices.stream()
                .map(ServiceMapper::toDTO)
                .collect(Collectors.toList());
    }
}
