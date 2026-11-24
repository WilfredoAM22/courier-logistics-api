package com.wilfredo.courier_logistics_api.exception;

public class ShipmentNotFoundException extends RuntimeException {
    public ShipmentNotFoundException(String trackingNumber) {
        super("No se encontró ningún envío con el código de rastreo: " + trackingNumber);
    }
}