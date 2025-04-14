package com.MyParkingLot.Damo.Service.orchestrator.parkingService;

import com.MyParkingLot.Damo.domain.Model.Vehicle;

public interface ParkingService {
    //void vehicleEntering();

    void vehicleEntering(Vehicle vehicle);

    void vehicleLeaving(Long vehicleId);
}
