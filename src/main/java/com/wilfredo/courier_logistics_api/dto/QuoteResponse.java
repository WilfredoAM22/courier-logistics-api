package com.wilfredo.courier_logistics_api.dto;

import java.math.BigDecimal;

public record QuoteResponse(
        BigDecimal billableWeight,
        BigDecimal fobValue,
        BigDecimal freightCost,
        BigDecimal customsDuty,
        BigDecimal selectiveTax,
        BigDecimal itbis,
        BigDecimal totalEstimatedCost,
        boolean taxExempt
) {
}