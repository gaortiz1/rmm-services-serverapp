package com.example.rmmservices.repositories;

import com.example.rmmservices.models.DeviceCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DeviceCostRepository extends JpaRepository<DeviceCost, Long> {

    List<DeviceCost> findByDeviceType_IdIn(Collection<Long> deviceTypeIds);

}
