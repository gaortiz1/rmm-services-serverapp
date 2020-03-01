package com.example.rmmservices.services.queries.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO implements Serializable {

    private UUID id;

    private String systemName;

}
