package com.example.rmmservices.services.commands;

import com.example.rmmservices.exceptions.CustomerNotFoundException;
import com.example.rmmservices.exceptions.ServiceNotFoundException;
import com.example.rmmservices.models.Customer;
import com.example.rmmservices.models.CustomerService;
import com.example.rmmservices.repositories.CustomerRepository;
import com.example.rmmservices.repositories.CustomerServiceRepository;
import com.example.rmmservices.repositories.ServiceRepository;
import com.example.rmmservices.services.commands.dtos.NewServiceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddServiceCommandService implements CommandService<NewServiceDTO> {

    private final CustomerRepository customerRepository;

    private final ServiceRepository serviceRepository;

    private final CustomerServiceRepository customerServiceRepository;

    @Autowired
    public AddServiceCommandService(CustomerRepository customerRepository,
                                    ServiceRepository serviceRepository,
                                    CustomerServiceRepository customerServiceRepository) {
        this.customerRepository = customerRepository;
        this.serviceRepository = serviceRepository;
        this.customerServiceRepository = customerServiceRepository;
    }


    @Override
    public Void handle(NewServiceDTO newServiceDTO) {

        log.debug("add new service: {}", newServiceDTO);

        Customer customer = this.customerRepository.findById(newServiceDTO.getCustomerId())
                .orElseThrow(CustomerNotFoundException::new);

        com.example.rmmservices.models.Service service = this.serviceRepository.findById(newServiceDTO.getServiceId())
                .orElseThrow(ServiceNotFoundException::new);

        CustomerService newCustomerService = CustomerService.builder()
                .customer(customer)
                .service(service)
                .build();

        this.customerServiceRepository.save(newCustomerService);

        return null;
    }
}
