package com.example.rmmservices.services.commands.dtos;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeviceDTO implements Serializable {

    @NotNull
    private Long deviceId;

    @NotEmpty
    private String systemName;
}
