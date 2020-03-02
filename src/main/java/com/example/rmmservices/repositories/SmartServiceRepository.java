package com.example.rmmservices.repositories;

import com.example.rmmservices.models.SmartService;
import com.example.rmmservices.models.ids.SmartServiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SmartServiceRepository extends JpaRepository<SmartService, SmartServiceId> {

    List<SmartService> findByServiceIdInAndAndDeviceTypeIdIn(Collection<Long> serviceIds, Collection<Long> deviceTypeIds);

}
