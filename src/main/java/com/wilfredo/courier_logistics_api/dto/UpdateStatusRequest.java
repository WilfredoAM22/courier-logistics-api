package com.wilfredo.courier_logistics_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.wilfredo.courier_logistics_api.domain.enums.PackageStatus;

public record UpdateStatusRequest (
        @NotNull(message = "El nuevo estado es obligatorio.")
        PackageStatus newStatus,
        @NotNull(message = "La ubicación actual es obligatoria.")
        String location,
        @Size(max = 255, message ="Las notas no pueden superar los 255 caracteres.")
        String notes
){}
