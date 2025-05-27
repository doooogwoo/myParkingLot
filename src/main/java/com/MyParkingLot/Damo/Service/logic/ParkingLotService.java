package com.MyParkingLot.Damo.Service.logic;

import com.MyParkingLot.Damo.Payload.dto.ParkingLotDto;

public interface ParkingLotService {

    void initParkingLot(String parkinglotName);

    void buildParkingLot(String parkingname, int ticketFee);

    ParkingLotDto getParkingLotInfoById(Long id);

    ParkingLotDto getParkingLotInfoByLocationLotId(String locationLotId);
}
