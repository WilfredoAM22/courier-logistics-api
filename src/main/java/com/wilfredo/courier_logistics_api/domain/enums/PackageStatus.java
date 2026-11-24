package com.wilfredo.courier_logistics_api.domain.enums;

public enum PackageStatus {
    RECEIVED_MIAMI("Recibido en almacén de Miami"),
    IN_TRANSIT("En vuelo / tránsito hacia RD"),
    CUSTOMS_CLEARANCE("En proceso de aduanas DGA"),
    READY_FOR_PICKUP("Disponible en sucursal para retiro o delivery"),
    DELIVERED("Entregado al cliente - estado final"),
    CANCELED("Pedido Cancelado");




    private final String description;

    PackageStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean canTransitionTo(PackageStatus nextStatus) {
        if (nextStatus == null) {
            return false;
        }
        return switch (this) {
            case RECEIVED_MIAMI -> nextStatus == IN_TRANSIT || nextStatus == CANCELED;
            case IN_TRANSIT -> nextStatus == CUSTOMS_CLEARANCE;
            case CUSTOMS_CLEARANCE -> nextStatus == READY_FOR_PICKUP;
            case READY_FOR_PICKUP -> nextStatus == DELIVERED;
            default -> false;
        };
    }
}
