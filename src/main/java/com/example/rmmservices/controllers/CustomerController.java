package com.example.rmmservices.controllers;

import com.example.rmmservices.services.queries.ServiceQuery;
import com.example.rmmservices.services.queries.dtos.ServiceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("customers")
public class CustomerController {

    private final ServiceQuery serviceQuery;

    @Autowired
    public CustomerController(ServiceQuery serviceQuery) {
        this.serviceQuery = serviceQuery;
    }

    @GetMapping("/{customerId}/services")
    public ResponseEntity<List<ServiceDTO>> findByCustomerId(@PathVariable Long customerId) {
        log.info("getting services {}", customerId);

        return ResponseEntity.ok(serviceQuery.findByCustomerId(customerId));
    }
}
