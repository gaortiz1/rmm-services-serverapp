package com.example.rmmservices.services.commands;

import com.example.rmmservices.exceptions.DeviceNotFoundException;
import com.example.rmmservices.models.Device;
import com.example.rmmservices.repositories.DeviceRepository;
import com.example.rmmservices.services.commands.UpdateDeviceCommandService;
import com.example.rmmservices.services.commands.dtos.UpdateDeviceDTO;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateDeviceCommandServiceTest {

    @InjectMocks
    private UpdateDeviceCommandService updateDeviceCommandService;

    @Mock
    private DeviceRepository deviceRepository;

    @Captor
    private ArgumentCaptor<Device> deviceCaptor;

    @Test
    void shouldUpdateDeviceWhenItExists() {
        Long deviceId = 1l;
        when(this.deviceRepository.findById(deviceId)).thenReturn(Optional.of(currentDevice(deviceId)));

        this.updateDeviceCommandService.handle(new UpdateDeviceDTO(deviceId, "new-pc-home"));

        verify(this.deviceRepository).save(deviceCaptor.capture());
        assertEquals("new-pc-home", deviceCaptor.getValue().getSystemName());
    }

    @Test
    void shouldNotUpdateDeviceWhenItNotExists() {

        when(this.deviceRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class,
                () -> updateDeviceCommandService.handle(new UpdateDeviceDTO(anyLong(), "new-pc-home")));
    }

    private Device currentDevice(Long id) {
        return Device.builder()
                .id(id)
                .systemName("pc-one")
                .build();
    }
}