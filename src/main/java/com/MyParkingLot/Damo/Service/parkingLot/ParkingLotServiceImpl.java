package com.MyParkingLot.Damo.Service.parkingLot;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotServiceImpl implements ParkingLotService{
    private final ParkingLotUtil parkingLotUtil;
    private final ModelMapper mapper;
    @Autowired
    public ParkingLotServiceImpl(ParkingLotUtil parkingLotUtil,
                                 ModelMapper mapper){
        this.parkingLotUtil = parkingLotUtil;
        this.mapper = mapper;
    }

    @Override
    public void initParkingLot(String parkinglotName){
        parkingLotUtil.initParkingLot(parkinglotName);
    }

    @Override
    public void buildParkingLot(String parkingname, int ticketFee){
        parkingLotUtil.createParkingLot(parkingname,ticketFee);
    }



}
