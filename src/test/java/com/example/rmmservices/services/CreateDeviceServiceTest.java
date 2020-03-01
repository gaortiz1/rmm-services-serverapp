package com.example.rmmservices.services;

import com.example.rmmservices.exceptions.CustomerNotFoundException;
import com.example.rmmservices.exceptions.DeviceTypeNotFoundException;
import com.example.rmmservices.models.Customer;
import com.example.rmmservices.models.Device;
import com.example.rmmservices.models.DeviceType;
import com.example.rmmservices.repositories.CustomerRepository;
import com.example.rmmservices.repositories.DeviceRepository;
import com.example.rmmservices.repositories.DeviceTypeRepository;
import com.example.rmmservices.services.commands.CreateDeviceService;
import com.example.rmmservices.services.commands.dtos.NewDeviceDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateDeviceServiceTest {

    @InjectMocks
    private CreateDeviceService createDeviceService;

    @Mock
    private DeviceTypeRepository deviceTypeRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @Captor
    private ArgumentCaptor<Device> deviceCaptor;

    @Test
    void shouldSaveDeviceWhenCustomerAndDeviceExists() {

        when(this.customerRepository.findById(anyLong())).thenReturn(Optional.of(newCustomer("customer one")));
        when(this.deviceTypeRepository.findById(anyLong())).thenReturn(Optional.of(newWindowsWorkstation()));

        createDeviceService.handle(new NewDeviceDTO("pc-one", 1l, 1l));

        verify(deviceRepository).save(deviceCaptor.capture());
        assertAll("assert ",
                () -> assertEquals("pc-one", deviceCaptor.getValue().getSystemName()),
                () -> assertNotNull(deviceCaptor.getValue().getCustomer()),
                () -> assertNotNull(deviceCaptor.getValue().getDeviceType())
        );
    }

    @Test
    void shouldNotSaveDeviceWhenCustomerNotExists() {

        when(this.customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> createDeviceService.handle(new NewDeviceDTO("pc-one", 1l, 1l)));
    }

    @Test
    void shouldNotSaveDeviceWhenDeviceTypeNotExists() {

        when(this.customerRepository.findById(anyLong())).thenReturn(Optional.of(newCustomer("customer one")));
        when(this.deviceTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DeviceTypeNotFoundException.class,
                () -> createDeviceService.handle(new NewDeviceDTO("pc-one", 1l, 1l)));
    }

    private Customer newCustomer(String name) {
        return Customer.builder()
                .id(1l)
                .name("customer")
                .build();
    }

    private DeviceType newWindowsWorkstation() {
        return DeviceType.builder()
                .id(1l)
                .name("Windows Workstation")
                .build();
    }
}