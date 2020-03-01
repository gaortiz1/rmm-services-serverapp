package com.example.rmmservices.services.dtos;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeviceDTO implements Serializable {

    @NotEmpty
    private UUID deviceId;

    @NotEmpty
    private String systemName;
}
