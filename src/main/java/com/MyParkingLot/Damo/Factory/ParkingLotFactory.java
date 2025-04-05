package com.MyParkingLot.Damo.Factory;

import com.MyParkingLot.Damo.Model.ParkingLot;
import com.MyParkingLot.Damo.Model.ParkingSpace;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@RequiredArgsConstructor //→ 自動幫我生成一個「只包含 final 欄位」的建構子
public class ParkingLotFactory {
    private final ParkingLotRepository parkingLotRepository;
    private final SpaceFactory spaceFactory;
    private final ParkingTicketFactory parkingTicketFactory;
    @Autowired
    public ParkingLotFactory(ParkingLotRepository parkingLotRepository,
                             SpaceFactory spaceFactory,
                             ParkingTicketFactory parkingTicketFactory){
        this.parkingLotRepository = parkingLotRepository;
        this.spaceFactory = spaceFactory;
        this.parkingTicketFactory = parkingTicketFactory;
    }
    public void initParkingLot(String parkingLotname){
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setCapacity(50);
        parkingLot.setFloors(1);
        parkingLot.setExpenses(10000);
        parkingLot.setParkingLotName(parkingLotname);

        parkingLotRepository.save(parkingLot);

        parkingTicketFactory.generateTicket(parkingLot);
        List<ParkingSpace> buildSpace = spaceFactory.generateSpaces(parkingLot,parkingLot.getCapacity());
        parkingLot.setParkingSpaceList(buildSpace);

        parkingLotRepository.save(parkingLot);
    }

    public void createParkingLot(String parkingLotName,int ticketPrice){
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setCapacity(50);
        parkingLot.setFloors(1);
        parkingLot.setExpenses(10000);
        parkingLot.setParkingLotName(parkingLotName);

        parkingLotRepository.save(parkingLot);

        parkingTicketFactory.setFee(parkingLot.getParkingLotId(), ticketPrice);
        List<ParkingSpace> buildSpace = spaceFactory.generateSpaces(parkingLot,parkingLot.getCapacity());
        parkingLot.setParkingSpaceList(buildSpace);

        parkingLotRepository.save(parkingLot);
    }

    
}
