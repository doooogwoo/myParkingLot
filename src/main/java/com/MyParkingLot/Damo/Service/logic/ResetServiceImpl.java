package com.MyParkingLot.Damo.Service.logic;

import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Repository.ParkingTicketRepository;
import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.Service.factory.ParkingLotFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetServiceImpl implements ResetService{
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingTicketRepository parkingTicketRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingLotFactory parkingLotFactory;
    @Override
    public void resetGameData(){
        parkingLotRepository.deleteAll();
        parkingTicketRepository.deleteAll();
        vehicleRepository.deleteAll();

        parkingLotFactory.initParkingLot("Origin Lot");
    }
}
