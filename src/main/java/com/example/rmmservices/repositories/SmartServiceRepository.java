package com.example.rmmservices.repositories;

import com.example.rmmservices.models.SmartService;
import com.example.rmmservices.models.ids.SmartServiceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SmartServiceRepository extends JpaRepository<SmartService, SmartServiceId> {

    List<SmartService> findByServiceIdInAndAndDeviceTypeIdIn(Collection<Long> serviceIds, Collection<Long> deviceTypeIds);

}
