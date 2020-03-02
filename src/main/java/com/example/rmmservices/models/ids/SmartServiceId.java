package com.example.rmmservices.models.ids;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmartServiceId implements Serializable {

    private Long serviceId;

    private Long deviceTypeId;
}
