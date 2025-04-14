package com.MyParkingLot.Damo.Service.FeeStrategy;

import com.MyParkingLot.Damo.domain.Model.Vehicle;

public class PlayerOverrideFeeStrategy implements FeeStrategy {
    private FeeStrategy defaulFeeStrategy;
    private FeeStrategy playerFeeStrategy;

    public PlayerOverrideFeeStrategy(FeeStrategy defaulFeeStrategy,
                                     FeeStrategy playerFeeStrategy) {
        this.defaulFeeStrategy = defaulFeeStrategy;
        this.playerFeeStrategy = playerFeeStrategy;
    }

    @Override
    public int calculateFee(Vehicle vehicle) {
        return playerFeeStrategy != null
                ? playerFeeStrategy.calculateFee(vehicle)
                : defaulFeeStrategy.calculateFee(vehicle);
    }
}
