package com.wilfredo.courier_logistics_api.repository;

import com.wilfredo.courier_logistics_api.domain.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;



public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    boolean existsByTrackingNumber(String trackingNumber);
    @Query("SELECT s FROM Shipment s LEFT JOIN FETCH s.events WHERE s.trackingNumber=:trackingNumber")
    Optional<Shipment> findByTrackingNumberWithEvents(@Param("trackingNumber")String trackingNumber);

}
