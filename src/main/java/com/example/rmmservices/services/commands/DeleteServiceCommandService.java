package com.example.rmmservices.services.commands;

import com.example.rmmservices.exceptions.CustomerNotFoundException;
import com.example.rmmservices.exceptions.ServiceNotFoundException;
import com.example.rmmservices.repositories.CustomerRepository;
import com.example.rmmservices.repositories.CustomerServiceRepository;
import com.example.rmmservices.repositories.ServiceRepository;
import com.example.rmmservices.services.commands.dtos.DeleteServiceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeleteServiceCommandService implements CommandService<DeleteServiceDTO> {

    private final CustomerRepository customerRepository;

    private final ServiceRepository serviceRepository;

    private final CustomerServiceRepository customerServiceRepository;

    @Autowired
    public DeleteServiceCommandService(CustomerRepository customerRepository,
                                    ServiceRepository serviceRepository,
                                    CustomerServiceRepository customerServiceRepository) {
        this.customerRepository = customerRepository;
        this.serviceRepository = serviceRepository;
        this.customerServiceRepository = customerServiceRepository;
    }

    @Override
    public Void handle(DeleteServiceDTO deleteServiceDTO) {

        log.debug("delete service: {}", deleteServiceDTO);

        if (!this.customerRepository.existsById(deleteServiceDTO.getCustomerId()))
            throw new CustomerNotFoundException();

        if (!this.serviceRepository.existsById(deleteServiceDTO.getServiceId()))
            throw new ServiceNotFoundException();

        this.customerServiceRepository
                .findByServiceIdAndCustomerId(deleteServiceDTO.getServiceId(), deleteServiceDTO.getCustomerId())
                .ifPresent(this.customerServiceRepository::delete);

        return null;
    }
}
