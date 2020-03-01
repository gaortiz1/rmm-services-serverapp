package com.example.rmmservices.services;

import com.example.rmmservices.exceptions.DeviceNotFoundException;
import com.example.rmmservices.models.Device;
import com.example.rmmservices.repositories.DeviceRepository;
import com.example.rmmservices.services.dtos.UpdateDeviceDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateDeviceServiceTest {

    @InjectMocks
    private UpdateDeviceService updateDeviceService;

    @Mock
    private DeviceRepository deviceRepository;

    @Captor
    private ArgumentCaptor<Device> deviceCaptor;

    @Test
    void shouldUpdateDeviceWhenItExists() {
        UUID deviceId = UUID.randomUUID();
        when(this.deviceRepository.findById(any(UUID.class))).thenReturn(Optional.of(currentDevice(deviceId)));

        this.updateDeviceService.handle(new UpdateDeviceDTO(deviceId, "new-pc-home"));

        verify(this.deviceRepository).save(deviceCaptor.capture());
        assertEquals("new-pc-home", deviceCaptor.getValue().getSystemName());
    }

    @Test
    void shouldNotUpdateDeviceWhenItNotExists() {

        when(this.deviceRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class,
                () -> updateDeviceService.handle(new UpdateDeviceDTO(UUID.randomUUID(), "new-pc-home")));
    }

    private Device currentDevice(UUID id) {
        return Device.builder()
                .id(id)
                .systemName("pc-one")
                .build();
    }
}