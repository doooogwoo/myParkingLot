package com.MyParkingLot.Damo.Service.factory;

import com.MyParkingLot.Damo.Service.observer.ParkingLotIncome;
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
@RequiredArgsConstructor
public class ParkingLotFactory {
    private final ParkingLotRepository parkingLotRepository;
    private final SpaceFactory spaceFactory;
    private final ParkingTicketFactory parkingTicketFactory;
    private final ParkingTicketRepository parkingTicketRepository;
    private final ParkingLotIncome parkingLotIncome;
    private ParkingLot buildParkingLot(String name, int floors, int spacePerFloor) {
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotName(name);
        lot.setFloors(floors);
        lot.setExpenses(10000); // 可後續改為參數
        lot.setCreateAt(LocalDateTime.now());
        parkingLotIncome.registerObserver(lot);


        parkingLotRepository.save(lot); // 先存，讓 spaces 有 foreign key

        List<ParkingSpace> spaces = spaceFactory.generateSpaces(lot, floors, spacePerFloor);
        lot.setParkingSpaceList(spaces);
        lot.setCapacity(spaces.size()); // 自動同步

        return lot;
    }

    public void initParkingLot(String parkingLotName) {
        ParkingLot lot = buildParkingLot(parkingLotName, 1, 50);
        parkingTicketFactory.generateTicket(lot);
        parkingLotRepository.save(lot);
    }

    public void createParkingLot(String parkingLotName, int ticketPrice) {
        ParkingLot lot = buildParkingLot(parkingLotName, 1, 50);

        ParkingTicket ticket = new ParkingTicket();
        ticket.setParkingLot(lot);
        ticket.setRate(ticketPrice);
        parkingTicketRepository.save(ticket);

        lot.setParkingTicket(ticket);
        parkingLotRepository.save(lot);
    }
}
