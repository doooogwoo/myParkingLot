package com.MyParkingLot.Damo.Payload.dto;

import lombok.Data;

@Data //考慮之後編碼用
public class ParkingSpaceViewDto {
    private Long parkingSpaceId;
    private boolean isOccupied;
    private String symbol;
    private String licenseCode; //顯示完整車牌
}
