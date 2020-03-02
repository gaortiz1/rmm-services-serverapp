package com.example.rmmservices.services.commands.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewServiceDTO implements Serializable {

    @NotNull
    private Long customerId;

    @NotNull
    private Long serviceId;
}
