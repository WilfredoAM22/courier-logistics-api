package com.wilfredo.courier_logistics_api.dto;

import com.wilfredo.courier_logistics_api.domain.enums.ProductCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record QuoteRequest(
        @NotNull(message = "El peso en libras es obligatorio")
        @DecimalMin(value = "0.01", message = "El peso debe ser mayor a 0.00 lbs")
        BigDecimal weightInLbs,

        @NotNull(message = "El valor FOB es obligatorio" )
        @DecimalMin(value = "0.00", message = "El valor FOB no puede ser negativo")
        BigDecimal fobValue,

        @NotNull(message = "La categoría del producto es obligatoria")
        ProductCategory category

        ){}