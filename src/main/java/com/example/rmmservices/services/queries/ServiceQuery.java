package com.example.rmmservices.services.queries;

import com.example.rmmservices.repositories.CustomerServiceRepository;
import com.example.rmmservices.services.queries.dtos.ServiceDTO;
import com.example.rmmservices.services.queries.mappers.ServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceQuery {

    private final CustomerServiceRepository customerServiceRepository;

    @Autowired
    public ServiceQuery(CustomerServiceRepository customerServiceRepository) {
        this.customerServiceRepository = customerServiceRepository;
    }

    public List<ServiceDTO> findByCustomerId(Long customerId) {
        return this.customerServiceRepository.findByCustomerId(customerId)
                .stream()
                .map(customerService -> ServiceMapper.toDTO(customerService.getService()))
                .collect(Collectors.toList());
    }
}
