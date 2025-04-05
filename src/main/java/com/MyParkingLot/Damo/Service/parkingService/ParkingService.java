package com.MyParkingLot.Damo.Service.parkingService;

import com.MyParkingLot.Damo.Model.Vehicle;

public interface ParkingService {
    //void vehicleEntering();

    void vehicleEntering(Vehicle vehicle);

    void vehicleLeaving(Long vehicleId);
}
