package com.example.rmmservices.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devices_cost", schema = "services")
public class DeviceCost implements Serializable {

    @Id
    private Long id;

    @NotNull
    @Column
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private DeviceType deviceType;
}
