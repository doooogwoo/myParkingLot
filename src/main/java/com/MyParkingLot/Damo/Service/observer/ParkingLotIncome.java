package com.MyParkingLot.Damo.Service.observer;

import com.MyParkingLot.Damo.Service.Command.LeaveVehicleCommand;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ParkingLotIncome implements ParkingCenter {
    private List<ParkingObserver> observers = new ArrayList<>();
    @Override
    public void registerObserver(ParkingObserver parkingLotObserver) {
        observers.add(parkingLotObserver);
    }

    @Override
    public void removeObserver(ParkingObserver parkingLotObserver) {
        observers.remove(parkingLotObserver);
    }

    @Override
    public void notifyObservers(VehicleEvent event) {
        for (ParkingObserver observer : observers) {
            observer.update(event);
        }
    }
}
