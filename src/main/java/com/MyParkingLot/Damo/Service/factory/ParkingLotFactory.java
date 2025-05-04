package com.MyParkingLot.Damo.Service.factory;

//import com.MyParkingLot.Damo.Service.observer.ParkingLotIncome;

import com.MyParkingLot.Damo.Exception.APIException;
import com.MyParkingLot.Damo.Payload.dto.location.LocationInfo;
import com.MyParkingLot.Damo.Service.logic.location.CityLocationManager;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.domain.Model.ParkingTicket;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Repository.ParkingTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParkingLotFactory {
    private final ParkingLotRepository parkingLotRepository;
    private final SpaceFactory spaceFactory;
    private final ParkingTicketFactory parkingTicketFactory;
    private final ParkingTicketRepository parkingTicketRepository;
    //private final ParkingLotIncome parkingLotIncome;
    private final CityLocationManager location;

    private ParkingLot buildParkingLot(String name, int floors, int spacePerFloor) {
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotName(name);
        lot.setFloors(floors);
        lot.setExpenses(10000); // 可後續改為參數
        lot.setCreateAt(LocalDateTime.now());


        parkingLotRepository.save(lot); // 先存，讓 spaces 有 foreign key

        List<ParkingSpace> spaces = spaceFactory.generateSpaces(lot, floors, spacePerFloor);
        lot.setParkingSpaceList(spaces);
        lot.setCapacity(spaces.size()); // 自動同步

        return lot;
    }

    public ParkingLot initParkingLot(String parkingLotName) {
        ParkingLot lot = buildParkingLot(parkingLotName, 1, 50);
        parkingLotRepository.save(lot);
        ParkingTicket ticket = parkingTicketFactory.generateTicket(lot);
        lot.setParkingTicket(ticket);

        LocationInfo info = location.getLocationInfo("L1");
        if (info == null || info.isUsed()){
            throw new APIException("L1 初始地點無法使用(不存在或已占用)");
        }
        lot.setLocationId("L1");
        lot.setY(info.getY());
        lot.setX(info.getX());
        location.setUsed("L1",true);

        parkingLotRepository.save(lot);
        return lot;
    }

    public ParkingLot createParkingLot(String parkingLotName, int ticketPrice) {
        ParkingLot lot = buildParkingLot(parkingLotName, 1, 50);

        //配置地點(隨機)
        Map.Entry<String,LocationInfo> infoEntry = location.getRandomAvailableLocation();
        LocationInfo info = infoEntry.getValue();

        lot.setLocationId(infoEntry.getKey());
        lot.setX(info.getX());
        lot.setY(info.getY());
        location.setUsed(infoEntry.getKey(), true);//記得標記

        //綁票價
        ParkingTicket ticket = new ParkingTicket();
        ticket.setParkingLot(lot);
        ticket.setRate(ticketPrice);
        parkingTicketRepository.save(ticket);

        lot.setParkingTicket(ticket);
        parkingLotRepository.save(lot);

        return lot;
    }
}
