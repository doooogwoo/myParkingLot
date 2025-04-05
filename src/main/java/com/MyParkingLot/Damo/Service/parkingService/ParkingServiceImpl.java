package com.MyParkingLot.Damo.Service.parkingService;

import com.MyParkingLot.Damo.Exception.APIException;
import com.MyParkingLot.Damo.Exception.BusinessException;
import com.MyParkingLot.Damo.Exception.ErrorCode;
import com.MyParkingLot.Damo.Exception.ResourceNotFoundException;
import com.MyParkingLot.Damo.Model.*;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Repository.ParkingSpaceRepository;
import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.Service.parkingTicket.ParkingTicketServiceImpl;
import com.MyParkingLot.Damo.Service.time.TimeManager;
import com.MyParkingLot.Damo.Factory.VehicleFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class ParkingServiceImpl implements ParkingService {
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final VehicleFactory vehicleFactory;
    private final TimeManager timeManager;
    private final VehicleRepository vehicleRepository;
    private final ParkingTicketServiceImpl parkingTicketService;
    private final ParkingLotRepository parkingLotRepository;

    @Autowired
    public ParkingServiceImpl(ParkingSpaceRepository parkingSpaceRepository,
                              VehicleFactory vehicleFactory,
                              TimeManager timeManager,
                              VehicleRepository vehicleRepository,
                              ParkingTicketServiceImpl parkingTicketService,
                              ParkingLotRepository parkingLotRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
        this.vehicleFactory = vehicleFactory;
        this.timeManager = timeManager;
        this.vehicleRepository = vehicleRepository;
        this.parkingTicketService = parkingTicketService;
        this.parkingLotRepository = parkingLotRepository;
    }

    @Override
    public void vehicleEntering(Vehicle vehicle) {

        //選擇聽車廠停入
        List<ParkingLot> parkingLotList = parkingLotRepository.findAll();
        ParkingLot parkingLot = randomParkingLot(parkingLotList);
        //排查停車場是否有空位
        List<ParkingSpace> spaces = parkingLot.getParkingSpaceList();
        List<ParkingSpace> availableSpaces = spaces
                .stream().filter(space -> !space.isOccupied())
                .toList();
        if (availableSpaces.isEmpty()) throw new APIException("沒有可用的停車位");
        Random random = new Random();
        ParkingSpace parkingSpace = availableSpaces.get(random.nextInt( availableSpaces.size()));
        //ParkingSpace parkingSpace = new ParkingSpace();
        vehicle.setParkingSpace(parkingSpace);

        vehicle.setVehicleEnterTime(timeManager.getCurrentGameTime());
        vehicle.assignParkingSpace(parkingSpace);


        if (!parkingSpace.isOccupied()) parkingSpace.setOccupied(true);

        ParkingSpaceType type = parkingSpace.getParkingSpaceType();
        if (type == ParkingSpaceType.HandicappedParkingSpace && !vehicle.isHandicapped()) {
            throw new BusinessException(ErrorCode.VEHICLE_ENTER_NOT_Handicapped);
        }
        if (type == ParkingSpaceType.ElectricParkingSpace && !vehicle.isElectricVehicle()) {
            throw new BusinessException(ErrorCode.VEHICLE_ENTER_NOT_Electric);
        }
        parkingSpaceRepository.save(parkingSpace);
    }


    @Override
    public void vehicleLeaving(Long vehicleId) {
        //先找車
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "vehicleId", vehicleId));
        //再找對應車輛的停車場
        ParkingSpace parkingSpace = vehicle.getParkingSpace();

        if (parkingSpace == null){
            throw new APIException("車子沒有車位");
        }
        //萬一車位為空(false)
        if (!parkingSpace.isOccupied()) {
            throw new APIException("離場前發現車位是空的");
        }

        //得到費用
        parkingSpace.setSpaceIncome(getSpaceIncome(vehicle));

        vehicle.setParkingSpace(null);
        parkingSpace.setVehicle(null);
        parkingSpace.setOccupied(false);


        vehicleRepository.save(vehicle);
        parkingSpaceRepository.save(parkingSpace);
    }

    //計算費用
    private int getSpaceIncome(Vehicle vehicle) {
        if (vehicle.getParkingDuration() == null) {
            throw new BusinessException(ErrorCode.VEHICLE_LEAVE_SPACE_NO_HOURS);
        }
        Duration vehicleDuration = vehicle.getParkingDuration();
        ParkingTicket parkingTicket = vehicle.getParkingSpace().getParkingLot().getParkingTicket();
        return parkingTicketService.calculateFee(vehicleDuration, parkingTicket.getParkingTicketId());
    }


    //隨機抽取停車場(考慮抽出擴展
    private ParkingLot randomParkingLot(List<ParkingLot> parkingLots) {
        Random random = new Random();
        if (parkingLots == null) throw new APIException("停車場未創建");
        int index = random.nextInt(parkingLots.size());
        return parkingLots.get(index);
    }

}
