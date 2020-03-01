package com.example.rmmservices.services.commands.dtos;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewDeviceDTO implements Serializable {

    @NotEmpty
    private String systemName;

    @NotEmpty
    private Long deviceTypeId;

    @NotEmpty
    private Long customerId;

}
