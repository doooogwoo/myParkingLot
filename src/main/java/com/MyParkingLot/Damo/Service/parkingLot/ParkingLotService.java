package com.MyParkingLot.Damo.Service.parkingLot;

import com.MyParkingLot.Damo.Payload.ParkingLotDto;

public interface ParkingLotService {

    void initParkingLot(String parkinglotName);

    void buildParkingLot(String parkingname, int ticketFee);
}
