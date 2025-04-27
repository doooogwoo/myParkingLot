package com.MyParkingLot.Damo.Service.observer;

import com.MyParkingLot.Damo.Service.Command.LeaveVehicleCommand;

public interface ParkingCenter {
    void registerObserver(ParkingObserver parkingLotObserver);
    void removeObserver(ParkingObserver parkingLotObserver);
    void notifyObservers(VehicleEvent event);
}
