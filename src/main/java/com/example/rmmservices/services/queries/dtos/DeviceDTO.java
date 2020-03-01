package com.example.rmmservices.services.queries.dtos;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO implements Serializable {

    private Long id;

    private String systemName;

}
