package com.MyParkingLot.Damo.Service.factory;

import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.domain.Model.ParkingTicket;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Repository.ParkingTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor //→ 自動幫我生成一個「只包含 final 欄位」的建構子
public class ParkingLotFactory {
    private final ParkingLotRepository parkingLotRepository;
    private final SpaceFactory spaceFactory;
    private final ParkingTicketFactory parkingTicketFactory;
    private final ParkingTicketRepository parkingTicketRepository;
    public void initParkingLot(String parkingLotname){
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setCapacity(50);
        parkingLot.setFloors(1);
        parkingLot.setExpenses(10000);
        parkingLot.setParkingLotName(parkingLotname);
        parkingLot.setCreateAt(LocalDateTime.now());

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
        parkingLot.setCreateAt(LocalDateTime.now());

        parkingLotRepository.save(parkingLot);
        ParkingTicket ticket = new ParkingTicket();
        ticket.setParkingLot(parkingLot);
        ticket.setRate(ticketPrice);
        parkingTicketRepository.save(ticket);

        parkingLot.setParkingTicket(ticket);

        List<ParkingSpace> buildSpace = spaceFactory.generateSpaces(parkingLot,parkingLot.getCapacity());
        parkingLot.setParkingSpaceList(buildSpace);

        parkingLotRepository.save(parkingLot);
    }

    
}
