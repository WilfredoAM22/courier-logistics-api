package com.wilfredo.courier_logistics_api.service;

import com.wilfredo.courier_logistics_api.domain.enums.PackageStatus;
import com.wilfredo.courier_logistics_api.dto.QuoteRequest;
import com.wilfredo.courier_logistics_api.dto.QuoteResponse;
import com.wilfredo.courier_logistics_api.dto.ShipmentTrackingResponse;
import com.wilfredo.courier_logistics_api.exception.InvalidStatusTransitionException;
import com.wilfredo.courier_logistics_api.exception.ShipmentNotFoundException;
import com.wilfredo.courier_logistics_api.repository.ShipmentRepository;
import org.springframework.transaction.annotation.Transactional;
import com.wilfredo.courier_logistics_api.dto.CreateShipmentRequest;
import org.springframework.stereotype.Service;
import com.wilfredo.courier_logistics_api.domain.model.Shipment;
import com.wilfredo.courier_logistics_api.domain.model.TrackingEvent;
import java.util.UUID;
import java.util.List;
import com.wilfredo.courier_logistics_api.dto.TrackingEventResponse;
import com.wilfredo.courier_logistics_api.dto.UpdateStatusRequest;
@Service
public class TrackingService {

    private final ShipmentRepository shipmentRepository;
    private final TariffEngineService tariffEngineService;

    public TrackingService(ShipmentRepository shipmentRepository, TariffEngineService tariffEngineService) {
        this.shipmentRepository = shipmentRepository;
        this.tariffEngineService = tariffEngineService;
    }
    @Transactional
    public ShipmentTrackingResponse createShipment(CreateShipmentRequest request) {
        QuoteRequest quoteRequest = new QuoteRequest(
                request.weightInLbs(),
                request.fobValue(),
                request.category()

        );
        QuoteResponse quote = tariffEngineService.calculateQuote(quoteRequest);

        String trackingNumber = generateUniqueTrackingNumber();
        Shipment shipment = new Shipment(
                trackingNumber,
                request.description(),
                request.weightInLbs(),
                request.fobValue(),
                quote.freightCost(),
                quote.customsDuty(),
                quote.selectiveTax(),
                quote.itbis(),
                quote.totalEstimatedCost(),
                request.category(),
                PackageStatus.RECEIVED_MIAMI
        );
        TrackingEvent initialEvent = new TrackingEvent(
                PackageStatus.RECEIVED_MIAMI,
                request.originLocation(),
                "Paquete recibido y registrado en almacén de origen"
        );
        shipment.addEvent(initialEvent);
        Shipment savedShipment = shipmentRepository.save(shipment);
        return mapToResponse(savedShipment);}


    @Transactional(readOnly = true)
    public ShipmentTrackingResponse getShipmentByTracking(String trackingNumber) {
        if (trackingNumber == null || trackingNumber.isBlank()) {
            throw new IllegalArgumentException("El número de seguimiento no puede estar vacío");
        }

        String normalizedTracking = trackingNumber.trim().toUpperCase();

        Shipment shipment = shipmentRepository.findByTrackingNumberWithEvents(normalizedTracking)
                .orElseThrow(() -> new ShipmentNotFoundException(normalizedTracking));
        return mapToResponse(shipment);
    }

        @Transactional
        public ShipmentTrackingResponse updateShipmentStatus(String trackingNumber, UpdateStatusRequest request) {
            if (trackingNumber == null || trackingNumber.isBlank()) {
                throw new IllegalArgumentException("El número de seguimiento no puede estar vacío");
            }
            String normalizedTracking = trackingNumber.trim().toUpperCase();
            Shipment shipment = shipmentRepository.findByTrackingNumberWithEvents(normalizedTracking)
                    .orElseThrow(() -> new ShipmentNotFoundException(normalizedTracking));

            if (!shipment.getCurrentStatus().canTransitionTo(request.newStatus()))
                throw new InvalidStatusTransitionException(
                        "No Puedes cambiar de " + shipment.getCurrentStatus() + " a " + request.newStatus()
                );
            shipment.setCurrentStatus(request.newStatus());


            TrackingEvent newEvent = new TrackingEvent(request.newStatus(), request.location(), request.notes());

            shipment.addEvent(newEvent);

            Shipment updatedShipment = shipmentRepository.save(shipment);

            return mapToResponse(updatedShipment);
    }

    private String generateUniqueTrackingNumber(
    ){
        String trackingNumber;
        do {
            trackingNumber = "DOM-MIA-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        } while (shipmentRepository.existsByTrackingNumber(trackingNumber));
        return trackingNumber;
        }
        private ShipmentTrackingResponse mapToResponse(Shipment shipment) {
            List<TrackingEventResponse> history = shipment.getEvents().stream()
                    .map(event -> new TrackingEventResponse(
                            event.getStatus(),
                            event.getStatus().getDescription(),
                            event.getLocation(),
                            event.getNotes(),
                            event.getTimestamp()))
                    .toList();

            return new ShipmentTrackingResponse(
                    shipment.getTrackingNumber(),
                    shipment.getDescription(),
                    shipment.getWeightInLbs(),
                    shipment.getCategory(),
                    shipment.getCurrentStatus(),
                    shipment.getCurrentStatus().getDescription(),
                    shipment.getFobValue(),
                    shipment.getFreightCost(),
                    shipment.getCustomsDuty(),
                    shipment.getSelectiveTax(),
                    shipment.getItbis(),
                    shipment.getTotalCost(),
                    shipment.getCreatedAt(),
                    history
            );
        }



    }
