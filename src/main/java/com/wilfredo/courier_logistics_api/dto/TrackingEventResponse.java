package com.wilfredo.courier_logistics_api.dto;

import com.wilfredo.courier_logistics_api.domain.enums.PackageStatus;

import java.time.LocalDateTime;

public record TrackingEventResponse (
        PackageStatus status,
        String statusDescription,
        String location,
        String notes,
        LocalDateTime timestamp

) {}
