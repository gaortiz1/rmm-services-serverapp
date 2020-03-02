package com.example.rmmservices.services.queries;

import com.example.rmmservices.models.*;
import com.example.rmmservices.repositories.CustomerServiceRepository;
import com.example.rmmservices.repositories.DeviceCostRepository;
import com.example.rmmservices.repositories.DeviceRepository;
import com.example.rmmservices.repositories.SmartServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CostQueryServicesTest {

    @InjectMocks
    private CostQueryServices costQueryServices;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private CustomerServiceRepository customerServiceRepository;

    @Mock
    private SmartServiceRepository smartServiceRepository;

    @Mock
    private DeviceCostRepository deviceCostRepository;

    @Test
    public void shouldComputeMonthlyCostWhenHasDevicesAndCustomerService() {

        Long customerId = 1l;

        when(this.deviceRepository
                .findByCustomer_Id(anyLong()))
                .thenReturn(Arrays.asList(
                        deviceWindowsServer(1l),
                        deviceWindowsWorkstation(2l),
                        deviceMac(3l),
                        deviceMac(4l),
                        deviceMac(5l)
                        ));

        when(this.customerServiceRepository
                .findByCustomerId(anyLong()))
                .thenReturn(Arrays.asList(
                        newCustomerAntivirus(customerId),
                        newCustomerCloudberry(customerId),
                        newCustomerTeamViewer(customerId)
                        ));

        when(this.deviceCostRepository.findByDeviceType_IdIn(anyCollection()))
                .thenReturn(Arrays.asList(
                        costOfWindowsWorkstation(1l),
                        costOfWindowsServer(2l),
                        costOfMac(3l)
                ));

        when(this.smartServiceRepository.findByServiceIdInAndAndDeviceTypeIdIn(anyCollection(), anyCollection()))
                .thenReturn(Arrays.asList(
                        costOfAntivirusForWindowsServer(),
                        costOfAntivirusForWindowsWorkstation(),
                        costOfAntivirusForMac(),
                        costOfCloudberryForWindowsServer(),
                        costOfCloudberryForWindowsWorkstation(),
                        costOfCloudberrysForMac(),
                        costOfTeamViewerForWindowsServer(),
                        costOfTeamViewerForWindowsWorkstation(),
                        costOfTeamViewerForMac()
                ));

        this.costQueryServices.computeMonthlyCost(customerId);


        BigDecimal cost = this.costQueryServices.computeMonthlyCost(customerId);

        assertEquals(BigDecimal.valueOf(65), cost);
    }

    @Test
    public void shouldNotComputeMonthlyCostWhenHasNotCustomerDevice() {

        Long customerId = 1l;

        when(this.deviceRepository
                .findByCustomer_Id(anyLong()))
                .thenReturn(Arrays.asList(
                        deviceWindowsServer(1l),
                        deviceWindowsWorkstation(2l),
                        deviceMac(3l),
                        deviceMac(4l),
                        deviceMac(5l)
                ));

        when(this.customerServiceRepository
                .findByCustomerId(anyLong()))
                .thenReturn(Collections.emptyList());

        this.costQueryServices.computeMonthlyCost(customerId);


        assertEquals(BigDecimal.ZERO, this.costQueryServices.computeMonthlyCost(customerId));
    }

    @Test
    public void shouldNotComputeMonthlyCostWhenHasNotDevicese() {

        Long customerId = 1l;

        when(this.deviceRepository
                .findByCustomer_Id(anyLong()))
                .thenReturn(Collections.emptyList());

        this.costQueryServices.computeMonthlyCost(customerId);


        assertEquals(BigDecimal.ZERO, this.costQueryServices.computeMonthlyCost(customerId));
    }

    private Device deviceWindowsWorkstation(Long id) {
        return Device.builder()
                .id(id)
                .systemName("pc-one")
                .deviceType(newDeviceTypeWindowsWorkstation())
                .build();
    }

    private Device deviceWindowsServer(Long id) {
        return Device.builder()
                .id(id)
                .systemName("pc-server")
                .deviceType(newDeviceTypeWindowsServer())
                .build();
    }

    private Device deviceMac(Long id) {
        return Device.builder()
                .id(id)
                .systemName("mac-home")
                .deviceType(newDeviceTypeMac())
                .build();
    }

    private CustomerService newCustomerAntivirus(Long customerId) {
        return CustomerService.builder()
                .service(newAntivirus())
                .customer(newCustomer(customerId))
                .build();
    }

    private CustomerService newCustomerCloudberry(Long id) {
        return CustomerService.builder()
                .id(id)
                .service(newCloudberry())
                .customer(newCustomer(id))
                .build();
    }

    private CustomerService newCustomerTeamViewer(Long id) {
        return CustomerService.builder()
                .id(id)
                .service(newTeamViewer())
                .customer(newCustomer(id))
                .build();
    }

    private Service newAntivirus() {
        return Service.builder()
                .id(1l)
                .name("Antivirus")
                .build();
    }

    private Service newCloudberry() {
        return Service.builder()
                .id(2l)
                .name("Cloudberry")
                .build();
    }

    private Service newTeamViewer() {
        return Service.builder()
                .id(3l)
                .name("TeamViewer")
                .build();
    }

    private Customer newCustomer(Long id) {
        return Customer.builder()
                .id(id)
                .name("customer")
                .build();
    }

    private DeviceCost costOfWindowsWorkstation(Long id) {
        return DeviceCost.builder()
                .id(id)
                .cost(BigDecimal.valueOf(4))
                .deviceType(newDeviceTypeWindowsWorkstation())
                .build();
    }

    private DeviceCost costOfWindowsServer(Long id) {
        return DeviceCost.builder()
                .id(id)
                .cost(BigDecimal.valueOf(4))
                .deviceType(newDeviceTypeWindowsServer())
                .build();
    }

    private DeviceCost costOfMac(Long id) {
        return DeviceCost.builder()
                .id(id)
                .cost(BigDecimal.valueOf(4))
                .deviceType(newDeviceTypeMac())
                .build();
    }

    private DeviceType newDeviceTypeWindowsWorkstation() {
        return DeviceType.builder()
                .id(1l)
                .name("Windows Workstation")
                .build();
    }

    private DeviceType newDeviceTypeWindowsServer() {
        return DeviceType.builder()
                .id(2l)
                .name("Windows Server")
                .build();
    }

    private DeviceType newDeviceTypeMac() {
        return DeviceType.builder()
                .id(3l)
                .name("Mac")
                .build();
    }

    private SmartService costOfAntivirusForWindowsServer() {
        return SmartService.builder()
                .cost(BigDecimal.valueOf(5))
                .deviceTypeId(newDeviceTypeWindowsServer().getId())
                .build();
    }

    private SmartService costOfAntivirusForWindowsWorkstation() {
        return SmartService.builder()
                .cost(BigDecimal.valueOf(5))
                .deviceTypeId(newDeviceTypeWindowsWorkstation().getId())
                .build();
    }

    private SmartService costOfAntivirusForMac() {
        return SmartService.builder()
                .cost(BigDecimal.valueOf(5))
                .deviceTypeId(newDeviceTypeMac().getId())
                .build();
    }

    private SmartService costOfCloudberryForWindowsServer() {
        return SmartService.builder()
                .cost(BigDecimal.valueOf(3))
                .deviceTypeId(newDeviceTypeWindowsServer().getId())
                .build();
    }

    private SmartService costOfCloudberryForWindowsWorkstation() {
        return SmartService.builder()
                .cost(BigDecimal.valueOf(3))
                .deviceTypeId(newDeviceTypeWindowsWorkstation().getId())
                .build();
    }

    private SmartService costOfCloudberrysForMac() {
        return SmartService.builder()
                .cost(BigDecimal.valueOf(3))
                .deviceTypeId(newDeviceTypeMac().getId())
                .build();
    }

    private SmartService costOfTeamViewerForWindowsServer() {
        return SmartService.builder()
                .cost(BigDecimal.ONE)
                .deviceTypeId(newDeviceTypeWindowsServer().getId())
                .build();
    }

    private SmartService costOfTeamViewerForWindowsWorkstation() {
        return SmartService.builder()
                .cost(BigDecimal.ONE)
                .deviceTypeId(newDeviceTypeWindowsWorkstation().getId())
                .build();
    }

    private SmartService costOfTeamViewerForMac() {
        return SmartService.builder()
                .cost(BigDecimal.ONE)
                .deviceTypeId(newDeviceTypeMac().getId())
                .build();
    }
}