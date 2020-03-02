package com.example.rmmservices.models;

import com.example.rmmservices.models.ids.SmartServiceId;
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
@Table(name = "smart_services", schema = "services")
@IdClass(SmartServiceId.class)
public class SmartService implements Serializable {

    @Id
    @Column(name = "service_id", insertable = false, updatable = false)
    private Long serviceId;

    @Id
    @Column(name = "type_id", insertable = false, updatable = false)
    private Long deviceTypeId;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private DeviceType deviceType;

    @NotNull
    private BigDecimal cost;
}
