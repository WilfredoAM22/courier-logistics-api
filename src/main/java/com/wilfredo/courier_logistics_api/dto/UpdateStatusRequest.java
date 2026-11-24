package com.wilfredo.courier_logistics_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.wilfredo.courier_logistics_api.domain.enums.PackageStatus;

public record UpdateStatusRequest(
        @NotNull(message = "El nuevo estado es obligatorio")
        PackageStatus newStatus,

        @NotBlank(message = "La ubicación es obligatoria")
        String location,

        String notes
) {}
