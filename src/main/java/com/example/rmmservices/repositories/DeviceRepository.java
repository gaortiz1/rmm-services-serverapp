package com.example.rmmservices.repositories;

import com.example.rmmservices.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findByCustomer_Id(Long customerId);

}
