package com.wilfredo.courier_logistics_api.exception;

public class ShipmentNotFoundException extends RuntimeException {

    public ShipmentNotFoundException(String message) {
        super(message);
    }
}
