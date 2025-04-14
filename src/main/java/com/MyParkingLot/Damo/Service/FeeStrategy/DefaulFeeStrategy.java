package com.MyParkingLot.Damo.Service.FeeStrategy;

import com.MyParkingLot.Damo.domain.Model.Vehicle;

public class DefaulFeeStrategy implements FeeStrategy{
    @Override
    public int calculateFee(Vehicle vehicle) {
        long hours = vehicle.getParkingDuration().toHours();
        return (int) hours * 30;
    }
}
