package com.MyParkingLot.Damo.Service.FeeStrategy;

import com.MyParkingLot.Damo.Exception.APIException;
import com.MyParkingLot.Damo.domain.Model.Vehicle;

public class PlayerFeeStrategy implements FeeStrategy{
    private final int rateHour;

    public PlayerFeeStrategy(int rateHour) {
        if (rateHour <0){
            throw new APIException("費率設定為負!!");
        }
        this.rateHour = rateHour;
    }
    @Override
    public int calculateFee(Vehicle vehicle) {
        long hours = vehicle.getParkingDuration().toHours();
        return (int) hours * rateHour;
    }
}
