package com.example.rmmservices.services.queries;

import com.example.rmmservices.models.CustomerService;
import com.example.rmmservices.models.Device;
import com.example.rmmservices.models.DeviceCost;
import com.example.rmmservices.models.SmartService;
import com.example.rmmservices.repositories.CustomerServiceRepository;
import com.example.rmmservices.repositories.DeviceCostRepository;
import com.example.rmmservices.repositories.DeviceRepository;
import com.example.rmmservices.repositories.SmartServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CostQueryServices {

    private final DeviceRepository deviceRepository;

    private final CustomerServiceRepository customerServiceRepository;

    private final SmartServiceRepository smartServiceRepository;

    private final DeviceCostRepository deviceCostRepository;

    @Autowired
    public CostQueryServices(DeviceRepository deviceRepository, CustomerServiceRepository customerServiceRepository, SmartServiceRepository smartServiceRepository, DeviceCostRepository deviceCostRepository) {
        this.deviceRepository = deviceRepository;
        this.customerServiceRepository = customerServiceRepository;
        this.smartServiceRepository = smartServiceRepository;
        this.deviceCostRepository = deviceCostRepository;
    }

    public BigDecimal computeMonthlyCost(Long customerId) {

        List<Device> devices = this.deviceRepository.findByCustomer_Id(customerId);

        if(devices.isEmpty()) return BigDecimal.ZERO;

        List<CustomerService> customerServices = this.customerServiceRepository.findByCustomerId(customerId);

        if(customerServices.isEmpty()) return BigDecimal.ZERO;

        List<Long> deviceTypeIds = devices
                .stream()
                .map(device -> device.getDeviceType().getId())
                .collect(Collectors.toList());

        List<Long> serviceIds = customerServices
                .stream()
                .map(customerService -> customerService.getService().getId())
                .collect(Collectors.toList());

        List<DeviceCost> devicesCost = this.deviceCostRepository.findByDeviceType_IdIn(deviceTypeIds);

        List<SmartService> services = this.smartServiceRepository.findByServiceIdInAndAndDeviceTypeIdIn(serviceIds, deviceTypeIds);

        return devices
                .stream()
                .map(device -> new CostByDevice(
                        filterSmartServiceByDeviceTypeId(device.getDeviceType().getId(), services),
                        filterDeviceCostByDeviceTypeId(device.getDeviceType().getId(), devicesCost))
                )
                .map(costByDevice -> costByDevice.costOfDevice().add(costByDevice.costOfServices()))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private List<SmartService> filterSmartServiceByDeviceTypeId(Long deviceTypeId, List<SmartService> services) {
        final Predicate<SmartService> deviceTypeIdPredicate = smartService -> Objects.equals(deviceTypeId, smartService.getDeviceTypeId());
        return services
                .stream()
                .filter(deviceTypeIdPredicate)
                .collect(Collectors.toList());
    }

    private DeviceCost filterDeviceCostByDeviceTypeId(Long deviceTypeId, List<DeviceCost> devicesCost) {
        final Predicate<DeviceCost> deviceTypeIdPredicate = deviceCost -> Objects.equals(deviceTypeId, deviceCost.getDeviceType().getId());
        return devicesCost
                .stream()
                .filter(deviceTypeIdPredicate)
                .findFirst()
                .get();
    }



    public class CostByDevice implements Serializable {
        private final List<SmartService> services;
        private final DeviceCost deviceCost;

        private CostByDevice(List<SmartService> services, DeviceCost deviceCost) {
            this.services = services;
            this.deviceCost = deviceCost;
        }

        public BigDecimal costOfServices() {
            return this.services
                    .stream()
                    .map(SmartService::getCost)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
        }

        public BigDecimal costOfDevice() {
            return this.deviceCost.getCost();
        }
    }

}
