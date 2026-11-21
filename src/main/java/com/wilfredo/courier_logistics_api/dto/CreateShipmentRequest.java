package com.wilfredo.courier_logistics_api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.wilfredo.courier_logistics_api.domain.enums.ProductCategory;
import java.math.BigDecimal;

public record CreateShipmentRequest(
        @NotBlank(message = "La descripcion del producto es obligatoria")
        @Size(min = 3, max = 150, message = "La descripcion de tu producto debe de tener entre 3 y 150 caracteres")
        String description,

        @NotNull(message = "El peso en libras es obligatorio.")
        @DecimalMin(value = "0.01", message = "El peso debe ser mayor o igual a 0.01 libras.")
        BigDecimal weightInLbs,

        @NotNull(message = "El valor FOB declarado es obligatorio.")
        @DecimalMin(value = "0.00", message = "El valor FOB no puede ser negativo.")
        BigDecimal fobValue,

        @NotNull(message = "La categoría del producto es obligatoria.")
        ProductCategory category,

        @NotBlank(message = "La ubicación de origen es obligatoria.")
        String originLocation
) {}
