package com.wilfredo.courier_logistics_api.dto;

import com.wilfredo.courier_logistics_api.domain.enums.PackageStatus;
import com.wilfredo.courier_logistics_api.domain.enums.ProductCategory;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ShipmentTrackingResponse (
       String TrackingNumber,
       String description,
       BigDecimal weightInLbs,
       ProductCategory category,
       PackageStatus currentStatus,
       String currentStatusDescription,
       BigDecimal fobValue,
       BigDecimal freightCost,
       BigDecimal customsDuty,
       BigDecimal selectiveTax,
       BigDecimal itbis,
       BigDecimal totalCost,
       LocalDateTime createdAt,
       List<TrackingEventResponse> history

) {}
