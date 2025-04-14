package com.MyParkingLot.Damo.Service.FeeStrategy;

import com.MyParkingLot.Damo.domain.Model.Vehicle;

public interface FeeStrategy {
    int calculateFee(Vehicle vehicle);
}
