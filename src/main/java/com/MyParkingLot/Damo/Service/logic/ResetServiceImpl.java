package com.MyParkingLot.Damo.Service.logic;

import com.MyParkingLot.Damo.Repository.*;
import com.MyParkingLot.Damo.Service.factory.ParkingLotFactory;
import com.MyParkingLot.Damo.Service.logic.location.CityLocationManager;
import com.MyParkingLot.Damo.Service.logic.location.LocationService;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.domain.Model.Player;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResetServiceImpl implements ResetService{
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingTicketRepository parkingTicketRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingLotFactory parkingLotFactory;
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final WeeklyReportRepository weeklyReportRepository;
    private final PlayerRepository playerRepository;
    private final CityLocationManager cityLocationManager;
    @Override
    @Transactional
    public void resetGameData(){
        cityLocationManager.resetAllLocations();
        //先清除連結
        List<ParkingSpace> spaces = parkingSpaceRepository.findAll();
        for(ParkingSpace space : spaces){
            space.setVehicle(null);
            space.setParkingLot(null);
        }
        parkingSpaceRepository.saveAll(spaces);
        parkingSpaceRepository.flush();

        weeklyReportRepository.deleteAllInBatch();

        parkingTicketRepository.deleteAllInBatch();
        vehicleRepository.deleteAllInBatch();
        parkingSpaceRepository.deleteAllInBatch();
        parkingLotRepository.deleteAllInBatch();
        playerRepository.deleteAllInBatch();

        //parkingLotFactory.initParkingLot("Origin Lot");交給init player
    }
}
