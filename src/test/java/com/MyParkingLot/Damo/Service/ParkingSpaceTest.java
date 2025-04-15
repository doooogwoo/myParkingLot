package com.MyParkingLot.Damo.Service;

import com.MyParkingLot.Damo.Exception.BusinessException;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.domain.Model.ParkingSpaceType;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingSpaceTest {

    @Test
    void testAssignVehicle_shouldSetBothSides() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setLicense("TEST-001");

        ParkingSpace space = new ParkingSpace();
        space.setParkingSpaceType(ParkingSpaceType.BaseParkingSpace);

        // Act
        space.assignVehicle(vehicle);

        // Assert
        assertTrue(space.isOccupied());
        assertEquals(vehicle, space.getVehicle());
        assertEquals(space, vehicle.getParkingSpace());
    }

    @Test
    void testUnassignVehicle_shouldClearBothSides() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        ParkingSpace space = new ParkingSpace();
        space.setParkingSpaceType(ParkingSpaceType.BaseParkingSpace);
        space.assignVehicle(vehicle);

        // Act
        space.unassignVehicle();
        vehicle.setLicense("TEST-123");
        space.setParkingSpaceId(99L); // 如果你手動給 id，log 就會顯示

// 或 mock ID：
        ReflectionTestUtils.setField(space, "parkingSpaceId", 99L);


        // Assert
        assertFalse(space.isOccupied());
        assertNull(space.getVehicle());
        assertNull(vehicle.getParkingSpace());
    }

    @Test
    void testAssignVehicle_whenAlreadyOccupied_shouldThrow() {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setLicense("OCCUPIED");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLicense("SECOND");

        ParkingSpace space = new ParkingSpace();
        space.setParkingSpaceType(ParkingSpaceType.BaseParkingSpace);
        space.assignVehicle(vehicle1);

        assertThrows(BusinessException.class, () -> space.assignVehicle(vehicle2));
    }

    @Test
    void testAssignVehicle_whenNotHandicappedToHandicappedSpace_shouldThrow() {
        Vehicle vehicle = new Vehicle();
        vehicle.setHandicapped(false);

        ParkingSpace space = new ParkingSpace();
        space.setParkingSpaceType(ParkingSpaceType.HandicappedParkingSpace);

        assertThrows(BusinessException.class, () -> space.assignVehicle(vehicle));
    }

    @Test
    void testAssignVehicle_whenNotElectricToElectricSpace_shouldThrow() {
        Vehicle vehicle = new Vehicle();
        vehicle.setElectricVehicle(false);

        ParkingSpace space = new ParkingSpace();
        space.setParkingSpaceType(ParkingSpaceType.ElectricParkingSpace);

        assertThrows(BusinessException.class, () -> space.assignVehicle(vehicle));
    }

    @Test
    void testUnassignVehicle_whenSpaceIsEmpty_shouldThrow() {
        ParkingSpace space = new ParkingSpace();
        space.setOccupied(false);
        space.setVehicle(null);

        assertThrows(BusinessException.class, space::unassignVehicle);
    }

    @Test
    void testAssign_thenUnassign_thenReassign_shouldWork() {
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        ParkingSpace space = new ParkingSpace();
        space.setParkingSpaceType(ParkingSpaceType.BaseParkingSpace);

        space.assignVehicle(vehicle1);
        space.unassignVehicle();
        space.assignVehicle(vehicle2);

        assertEquals(vehicle2, space.getVehicle());
        assertEquals(space, vehicle2.getParkingSpace());
    }

    @Test
    void testUnassignVehicle_shouldClearBothSides1() {
        Vehicle vehicle = new Vehicle();
        ParkingSpace space = new ParkingSpace();
        space.assignVehicle(vehicle); // 讓雙向綁定成立

        space.unassignVehicle(); // 測試會不會錯誤

        assertFalse(space.isOccupied());
        assertNull(space.getVehicle());
        assertNull(vehicle.getParkingSpace());
    }

    @Test
    void assignVehicle_shouldSetBothSides() {
        ParkingSpace space = new ParkingSpace();
        space.setParkingSpaceType(ParkingSpaceType.BaseParkingSpace);
        space.setOccupied(false);

        Vehicle vehicle = new Vehicle();
        vehicle.setLicense("TEST-123");

        ParkingLot lot = new ParkingLot();
        lot.setParkingLotName("MyLot");
        space.setParkingLot(lot);

        space.assignVehicle(vehicle);

        assertTrue(space.isOccupied());
        assertEquals(vehicle, space.getVehicle());
        assertEquals(space, vehicle.getParkingSpace());
        assertEquals(lot, vehicle.getParkingLot());
    }

}
