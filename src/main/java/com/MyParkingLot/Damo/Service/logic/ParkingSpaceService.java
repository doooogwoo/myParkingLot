package com.MyParkingLot.Damo.Service.logic;

import com.MyParkingLot.Damo.Payload.dto.ParkingSpaceDto;
import com.MyParkingLot.Damo.Payload.dto.ParkingSpaceViewDto;

import java.util.List;

public interface ParkingSpaceService {
    List<ParkingSpaceViewDto> getSpaceStatusList(Long parkingLotId);
}
