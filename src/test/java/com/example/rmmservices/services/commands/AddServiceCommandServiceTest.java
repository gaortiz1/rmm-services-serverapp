package com.example.rmmservices.services.commands;

import com.example.rmmservices.exceptions.CustomerNotFoundException;
import com.example.rmmservices.exceptions.ServiceNotFoundException;
import com.example.rmmservices.models.Customer;
import com.example.rmmservices.models.CustomerService;
import com.example.rmmservices.models.Service;
import com.example.rmmservices.repositories.CustomerRepository;
import com.example.rmmservices.repositories.CustomerServiceRepository;
import com.example.rmmservices.repositories.ServiceRepository;
import com.example.rmmservices.services.commands.dtos.NewServiceDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddServiceCommandServiceTest {

    @InjectMocks
    private AddServiceCommandService addServiceCommandService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private CustomerServiceRepository customerServiceRepository;

    @Captor
    private ArgumentCaptor<CustomerService> deviceCaptor;

    @Test
    void shouldSaveCustomerServiceWhenCustomerAndServiceExists() {

        when(this.customerRepository.findById(anyLong())).thenReturn(Optional.of(newCustomer("customer one")));
        when(this.serviceRepository.findById(anyLong())).thenReturn(Optional.of(newAntivirus()));

        addServiceCommandService.handle(new NewServiceDTO(1l, 1l));

        verify(customerServiceRepository).save(deviceCaptor.capture());
        assertAll("assert ",
                () -> assertNotNull(deviceCaptor.getValue().getCustomer()),
                () -> assertNotNull(deviceCaptor.getValue().getService())
        );
    }

    @Test
    void shouldNotSaveDeviceWhenCustomerNotExists() {

        when(this.customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> addServiceCommandService.handle(new NewServiceDTO(1l, 1l)));
    }

    @Test
    void shouldNotSaveDeviceWhenDeviceTypeNotExists() {

        when(this.customerRepository.findById(anyLong())).thenReturn(Optional.of(newCustomer("customer one")));
        when(this.serviceRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ServiceNotFoundException.class,
                () -> addServiceCommandService.handle(new NewServiceDTO(1l, 1l)));
    }

    private Customer newCustomer(String name) {
        return Customer.builder()
                .id(1l)
                .name("customer")
                .build();
    }

    private Service newAntivirus() {
        return Service.builder()
                .id(1l)
                .name("antivirus")
                .build();
    }

}