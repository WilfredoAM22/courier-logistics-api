package com.wilfredo.courier_logistics_api.controller;

import com.wilfredo.courier_logistics_api.dto.UpdateStatusRequest;
import com.wilfredo.courier_logistics_api.service.TrackingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wilfredo.courier_logistics_api.dto.CreateShipmentRequest;
import com.wilfredo.courier_logistics_api.dto.ShipmentTrackingResponse;

@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {
    private final TrackingService trackingService;
    public ShipmentController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }
    @PostMapping
    public ResponseEntity<ShipmentTrackingResponse> registerShipment(@Valid @RequestBody CreateShipmentRequest request){
        var createdShipment = trackingService.createShipment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShipment);
    }


    @GetMapping("/{trackingNumber}")
    public ResponseEntity<ShipmentTrackingResponse>getShipment(@PathVariable String trackingNumber){
       return ResponseEntity.ok(trackingService.getShipmentByTracking(trackingNumber));
    }

    @PatchMapping("/{trackingNumber}/status")
    public ResponseEntity<ShipmentTrackingResponse>updateStatus(@PathVariable String trackingNumber, @Valid @RequestBody UpdateStatusRequest request){
        return ResponseEntity.ok(trackingService.updateShipmentStatus(trackingNumber,request));
    }
}
