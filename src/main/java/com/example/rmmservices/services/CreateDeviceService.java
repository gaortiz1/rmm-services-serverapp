package com.example.rmmservices.services;

import com.example.rmmservices.exceptions.CustomerNotFoundException;
import com.example.rmmservices.exceptions.DeviceTypeNotFoundException;
import com.example.rmmservices.models.Customer;
import com.example.rmmservices.models.Device;
import com.example.rmmservices.models.DeviceType;
import com.example.rmmservices.repositories.CustomerRepository;
import com.example.rmmservices.repositories.DeviceRepository;
import com.example.rmmservices.repositories.DeviceTypeRepository;
import com.example.rmmservices.services.dtos.NewDeviceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Slf4j
@Service
public class CreateDeviceService implements CommandService<NewDeviceDTO> {

    private final DeviceTypeRepository deviceTypeRepository;

    private final CustomerRepository customerRepository;

    private final DeviceRepository deviceRepository;

    @Autowired
    public CreateDeviceService(DeviceTypeRepository deviceTypeRepository, CustomerRepository customerRepository, DeviceRepository deviceRepository) {
        this.deviceTypeRepository = deviceTypeRepository;
        this.customerRepository = customerRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Void execute(@Valid NewDeviceDTO newDeviceDTO) {

        log.debug("Saving new device: {}", newDeviceDTO);

        final Customer customer = this.customerRepository.findById(newDeviceDTO.getCustomerId())
                .orElseThrow(CustomerNotFoundException::new);

        final DeviceType deviceType = this.deviceTypeRepository.findById(newDeviceDTO.getDeviceTypeId())
                .orElseThrow(DeviceTypeNotFoundException::new);

        final Device newDevice = Device.builder()
                .customer(customer)
                .deviceType(deviceType)
                .systemName(newDeviceDTO.getSystemName())
                .build();

        this.deviceRepository.save(newDevice);

        return null;
    }
}
