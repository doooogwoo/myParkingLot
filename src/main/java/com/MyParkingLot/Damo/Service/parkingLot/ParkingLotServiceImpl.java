package com.MyParkingLot.Damo.Service.parkingLot;

import com.MyParkingLot.Damo.Factory.ParkingLotFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotServiceImpl implements ParkingLotService{
    private final ParkingLotFactory parkingLotFactory;
    private final ModelMapper mapper;
    @Autowired
    public ParkingLotServiceImpl(ParkingLotFactory parkingLotFactory,
                                 ModelMapper mapper){
        this.parkingLotFactory = parkingLotFactory;
        this.mapper = mapper;
    }

    @Override
    public void initParkingLot(String parkinglotName){
        parkingLotFactory.initParkingLot(parkinglotName);
    }

    @Override
    public void buildParkingLot(String parkingname, int ticketFee){
        parkingLotFactory.createParkingLot(parkingname,ticketFee);
    }



}
