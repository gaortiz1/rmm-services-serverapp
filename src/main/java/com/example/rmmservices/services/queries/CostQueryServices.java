package com.example.rmmservices.services.queries;

import com.example.rmmservices.models.CustomerService;
import com.example.rmmservices.models.Device;
import com.example.rmmservices.models.SmartService;
import com.example.rmmservices.repositories.CustomerServiceRepository;
import com.example.rmmservices.repositories.DeviceRepository;
import com.example.rmmservices.repositories.SmartServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CostQueryServices {

    private final DeviceRepository deviceRepository;

    private final CustomerServiceRepository customerServiceRepository;

    private final SmartServiceRepository smartServiceRepository;

    @Autowired
    public CostQueryServices(DeviceRepository deviceRepository, CustomerServiceRepository customerServiceRepository, SmartServiceRepository smartServiceRepository) {
        this.deviceRepository = deviceRepository;
        this.customerServiceRepository = customerServiceRepository;
        this.smartServiceRepository = smartServiceRepository;
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

        List<SmartService> services = this.smartServiceRepository.findByServiceIdInAndAndDeviceTypeIdIn(serviceIds, deviceTypeIds);

        return devices
                .stream()
                .map(computeCostByDevice(services))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private Function<Device, @NotNull BigDecimal> computeCostByDevice(List<SmartService> services) {
        return device -> services
                .stream()
                .filter(smartService -> smartService.getDeviceTypeId().equals(device.getDeviceType().getId()))
                .map(SmartService::getCost)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

}
