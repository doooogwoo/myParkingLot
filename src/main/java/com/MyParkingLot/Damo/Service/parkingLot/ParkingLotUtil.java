package com.MyParkingLot.Damo.Service.parkingLot;

import com.MyParkingLot.Damo.Model.ParkingLot;
import com.MyParkingLot.Damo.Model.ParkingSpace;
import com.MyParkingLot.Damo.Payload.ParkingLotDto;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Service.parkingSpace.SpaceGenerate;
import com.MyParkingLot.Damo.Service.parkingTicket.ParkingTicketGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@RequiredArgsConstructor //→ 自動幫我生成一個「只包含 final 欄位」的建構子
public class ParkingLotUtil {
    private final ParkingLotRepository parkingLotRepository;
    private final SpaceGenerate spaceGenerate;
    private final ParkingTicketGenerate parkingTicketGenerate;
    @Autowired
    public ParkingLotUtil(ParkingLotRepository parkingLotRepository,
                          SpaceGenerate spaceGenerate,
                          ParkingTicketGenerate parkingTicketGenerate){
        this.parkingLotRepository = parkingLotRepository;
        this.spaceGenerate = spaceGenerate;
        this.parkingTicketGenerate = parkingTicketGenerate;
    }
    public void initParkingLot(String parkingLotname){
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setCapacity(50);
        parkingLot.setFloors(1);
        parkingLot.setExpenses(10000);
        parkingLot.setParkingLotName(parkingLotname);

        parkingLotRepository.save(parkingLot);

        parkingTicketGenerate.generateTicket(parkingLot);
        List<ParkingSpace> buildSpace = spaceGenerate.generateSpaces(parkingLot,parkingLot.getCapacity());
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

        parkingTicketGenerate.setFee(parkingLot.getParkingLotId(), ticketPrice);
        List<ParkingSpace> buildSpace = spaceGenerate.generateSpaces(parkingLot,parkingLot.getCapacity());
        parkingLot.setParkingSpaceList(buildSpace);

        parkingLotRepository.save(parkingLot);
    }

    
}
