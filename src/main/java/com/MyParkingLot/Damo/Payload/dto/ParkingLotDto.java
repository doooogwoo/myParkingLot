package com.MyParkingLot.Damo.Payload.dto;

import lombok.Data;

import java.util.List;
@Data
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
    private ParkingTicketDto parkingTicketDto;
    private Long playerId;
    private List<ParkingSpaceDto> parkingSpaceList;
 private String createAt;

}