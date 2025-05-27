package com.MyParkingLot.Damo.Service.factory;

import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.domain.Model.ParkingSpaceType;
import com.MyParkingLot.Damo.Repository.ParkingSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpaceFactory {
    private final ParkingSpaceRepository parkingSpaceRepository;
    @Autowired
    public SpaceFactory(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    public List<ParkingSpace> generateSpaces(ParkingLot parkingLot, int floors, int spacePerFloor) {
        List<ParkingSpace> spaces = new ArrayList<>();

        for (int floor = 1; floor <= floors; floor++) {
            for (int i = 0; i < spacePerFloor; i++) {
                ParkingSpace parkingSpace = new ParkingSpace();
                parkingSpace.setOccupied(false);
                parkingSpace.setParkingLot(parkingLot);
                parkingSpace.setParkingSpaceType(ParkingSpaceType.BaseParkingSpace);
                parkingSpace.setSpaceIncome(0);
                parkingSpace.setFloor(floor);
                spaces.add(parkingSpace);
            }
        }

        parkingSpaceRepository.saveAll(spaces);
        return spaces;
    }

}
