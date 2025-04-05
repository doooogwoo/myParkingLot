package com.MyParkingLot.Damo.Factory;

import com.MyParkingLot.Damo.Model.ParkingLot;
import com.MyParkingLot.Damo.Model.ParkingSpace;
import com.MyParkingLot.Damo.Model.ParkingSpaceType;
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

    public List<ParkingSpace> generateSpaces(ParkingLot parkingLot, int count){
        List<ParkingSpace> spaces = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ParkingSpace parkingSpace = new ParkingSpace();
            parkingSpace.setOccupied(false);
            parkingSpace.setParkingLot(parkingLot);
            parkingSpace.setParkingSpaceType(ParkingSpaceType.BaseParkingSpace);
            parkingSpace.setSpaceIncome(0);

            spaces.add(parkingSpace);
        }
        parkingSpaceRepository.saveAll(spaces);
        return spaces;
    }
}
