package com.example.rmmservices.services.commands;

import com.example.rmmservices.exceptions.DeviceNotFoundException;
import com.example.rmmservices.models.Device;
import com.example.rmmservices.repositories.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteDeviceCommandServiceTest {

    @InjectMocks
    private DeleteDeviceCommandService deleteDeviceCommandService;

    @Mock
    private DeviceRepository deviceRepository;

    @Test
    void shouldDeleteDeviceWhenItExists() {
        Long deviceId = 1l;
        when(this.deviceRepository.findById(anyLong())).thenReturn(Optional.of(currentDevice(deviceId)));

        this.deleteDeviceCommandService.handle(deviceId);

        verify(this.deviceRepository).deleteById(deviceId);
    }

    @Test
    void shouldNotDeleteDeviceWhenItNotExists() {
        when(this.deviceRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class,
                () -> deleteDeviceCommandService.handle(1l));

        verify(this.deviceRepository, never()).deleteById(anyLong());
    }

    private Device currentDevice(Long id) {
        return Device.builder()
                .id(id)
                .systemName("pc-one")
                .build();
    }
}