package com.example.rmmservices.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devices")
public class Device implements Serializable {

    @Id
    private UUID id;

    @NotNull
    @Column(name = "system_name")
    private String systemName;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private DeviceType deviceType;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
