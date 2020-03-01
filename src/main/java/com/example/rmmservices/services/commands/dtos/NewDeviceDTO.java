package com.example.rmmservices.services.commands.dtos;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewDeviceDTO implements Serializable {

    @NotEmpty
    private String systemName;

    @NotNull
    private Long deviceTypeId;

    @NotNull
    private Long customerId;

}
