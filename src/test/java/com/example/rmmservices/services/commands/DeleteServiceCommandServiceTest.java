package com.example.rmmservices.services.commands;

import com.example.rmmservices.exceptions.CustomerNotFoundException;
import com.example.rmmservices.exceptions.ServiceNotFoundException;
import com.example.rmmservices.models.Customer;
import com.example.rmmservices.models.CustomerService;
import com.example.rmmservices.models.Service;
import com.example.rmmservices.repositories.CustomerRepository;
import com.example.rmmservices.repositories.CustomerServiceRepository;
import com.example.rmmservices.repositories.ServiceRepository;
import com.example.rmmservices.services.commands.dtos.DeleteServiceDTO;
import com.example.rmmservices.services.commands.dtos.NewServiceDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteServiceCommandServiceTest {

    @InjectMocks
    private DeleteServiceCommandService deleteServiceCommandService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private CustomerServiceRepository customerServiceRepository;

    @Test
    void shouldDeleteCustomerServiceWhenItExists() {
        when(this.customerRepository.existsById(anyLong())).thenReturn(Boolean.TRUE);
        when(this.serviceRepository.existsById(anyLong())).thenReturn(Boolean.TRUE);
        when(this.customerServiceRepository.findByServiceIdAndCustomerId(anyLong(), anyLong()))
                .thenReturn(Optional.of(newCustomerServicer()));

        this.deleteServiceCommandService.handle(new DeleteServiceDTO(1l, 1l));

        verify(this.customerServiceRepository).delete(any(CustomerService.class));
    }

    @Test
    void shouldNotDeleteCustomerServiceWhenCustomerNotExists() {

        when(this.customerRepository.existsById(anyLong())).thenReturn(Boolean.FALSE);

        assertThrows(CustomerNotFoundException.class,
                () -> deleteServiceCommandService.handle(new DeleteServiceDTO(1l, 1l)));
    }

    @Test
    void shouldNotDeleteCustomerServiceWhenServiceNotExists() {

        when(this.customerRepository.existsById(anyLong())).thenReturn(Boolean.TRUE);
        when(this.serviceRepository.existsById(anyLong())).thenReturn(Boolean.FALSE);

        assertThrows(ServiceNotFoundException.class,
                () -> deleteServiceCommandService.handle(new DeleteServiceDTO(1l, 1l)));
    }

    @Test
    void shouldNotDeleteCustomerServiceWhenItNotExists() {

        when(this.customerRepository.existsById(anyLong())).thenReturn(Boolean.TRUE);
        when(this.serviceRepository.existsById(anyLong())).thenReturn(Boolean.TRUE);

        when(this.customerServiceRepository.findByServiceIdAndCustomerId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        this.deleteServiceCommandService.handle(new DeleteServiceDTO(1l, 1l));

        verify(this.customerServiceRepository, never()).delete(any(CustomerService.class));
    }

    private CustomerService newCustomerServicer() {
        return CustomerService.builder()
                .customer(newCustomer("customer"))
                .service(newAntivirus())
                .build();
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