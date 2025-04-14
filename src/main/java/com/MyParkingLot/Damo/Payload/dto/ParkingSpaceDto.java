package com.MyParkingLot.Damo.Payload.dto;

import com.MyParkingLot.Damo.domain.Model.ParkingSpaceType;
import lombok.Data;

@Data
public class ParkingSpaceDto {
    private Long parkingSpaceId;
    private ParkingSpaceType parkingSpaceType;
    private boolean isOccupied;
    private int spaceIncome;
    private VehicleDto vehicleDto;
    private Long parkingLotDtoId;
    private String symbol;
}
