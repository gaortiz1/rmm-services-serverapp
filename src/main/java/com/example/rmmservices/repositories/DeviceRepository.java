package com.example.rmmservices.repositories;

import com.example.rmmservices.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {

    List<Device> findByCustomer_Id(Long customerId);

}
