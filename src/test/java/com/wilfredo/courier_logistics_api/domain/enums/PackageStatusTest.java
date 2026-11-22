package com.wilfredo.courier_logistics_api.domain.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PackageStatusTest {

    @Test
    void shouldAllowValidSequentialTransitions() {
        assertTrue(PackageStatus.RECEIVED_MIAMI.canTransitionTo(PackageStatus.IN_TRANSIT));
        assertTrue(PackageStatus.IN_TRANSIT.canTransitionTo(PackageStatus.CUSTOMS_CLEARANCE));
        assertTrue(PackageStatus.CUSTOMS_CLEARANCE.canTransitionTo(PackageStatus.READY_FOR_PICKUP));
        assertTrue(PackageStatus.READY_FOR_PICKUP.canTransitionTo(PackageStatus.DELIVERED));
    }

    @Test
    void shouldBlockIllegalTransitionsAndTerminalState() {
        assertFalse(PackageStatus.RECEIVED_MIAMI.canTransitionTo(PackageStatus.DELIVERED));
        assertFalse(PackageStatus.RECEIVED_MIAMI.canTransitionTo(PackageStatus.CUSTOMS_CLEARANCE));
        assertFalse(PackageStatus.DELIVERED.canTransitionTo(PackageStatus.READY_FOR_PICKUP));
        assertFalse(PackageStatus.DELIVERED.canTransitionTo(PackageStatus.RECEIVED_MIAMI));
        assertFalse(PackageStatus.DELIVERED.canTransitionTo(PackageStatus.IN_TRANSIT));
        assertFalse(PackageStatus.DELIVERED.canTransitionTo(PackageStatus.CUSTOMS_CLEARANCE));
    }
}
