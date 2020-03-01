package com.example.rmmservices.services;

import com.example.rmmservices.exceptions.DeviceNotFoundException;
import com.example.rmmservices.models.Device;
import com.example.rmmservices.repositories.DeviceRepository;
import com.example.rmmservices.services.commands.DeleteDeviceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteDeviceServiceTest {

    @InjectMocks
    private DeleteDeviceService deleteDeviceService;

    @Mock
    private DeviceRepository deviceRepository;

    @Test
    void shouldDeleteDeviceWhenItExists() {
        UUID deviceId = UUID.randomUUID();
        when(this.deviceRepository.findById(any(UUID.class))).thenReturn(Optional.of(currentDevice(deviceId)));

        this.deleteDeviceService.handle(deviceId);

        verify(this.deviceRepository).deleteById(deviceId);
    }

    @Test
    void shouldNotDeleteDeviceWhenItNotExists() {
        when(this.deviceRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class,
                () -> deleteDeviceService.handle(UUID.randomUUID()));

        verify(this.deviceRepository, never()).deleteById(any(UUID.class));
    }

    private Device currentDevice(UUID id) {
        return Device.builder()
                .id(id)
                .systemName("pc-one")
                .build();
    }
}