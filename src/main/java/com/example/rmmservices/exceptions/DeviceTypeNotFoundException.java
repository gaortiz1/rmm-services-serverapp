package com.example.rmmservices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Device type not found")
public class DeviceTypeNotFoundException extends RuntimeException {
}
