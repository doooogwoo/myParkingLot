package com.MyParkingLot.Damo.Payload;

import com.MyParkingLot.Damo.Model.ParkingLot;
import com.MyParkingLot.Damo.Model.ParkingSpaceType;
import com.MyParkingLot.Damo.Model.Vehicle;
import jakarta.persistence.*;

public class ParkingSpaceDto {
    private Long parkingSpaceId;
    private ParkingSpaceType parkingSpaceType;
    private boolean isOccupied;
    private int spaceIncome;
    private VehicleDto vehicleDto;
    private Long parkingLotDtoId;
}
