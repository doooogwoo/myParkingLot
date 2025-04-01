package com.MyParkingLot.Damo.Payload;

import com.MyParkingLot.Damo.Model.ParkingSpace;
import java.util.List;

public class ParkingLotDto {
   private Long parkingLotId;
    private String parkingLotName;
    private int capacity;
    private int income; //收入
    private int expenses; //維修費用
    private int floors;
    private double handicapBonus;
    private String events;
    private boolean isFull;
    private boolean isHandicapFull;
    private boolean isElectricFull;
    private List<ParkingSpaceDto> parkingSpaceList;

}