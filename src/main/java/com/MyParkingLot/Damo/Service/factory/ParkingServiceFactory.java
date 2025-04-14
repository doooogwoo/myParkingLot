package com.MyParkingLot.Damo.Service.factory;

import com.MyParkingLot.Damo.Exception.APIException;
import com.MyParkingLot.Damo.Exception.BusinessException;
import com.MyParkingLot.Damo.Exception.ErrorCode;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Repository.ParkingSpaceRepository;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.domain.Model.ParkingSpaceType;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.Service.Command.EnterVehicleCommand;
import com.MyParkingLot.Damo.Service.Command.LeaveVehicleCommand;
import com.MyParkingLot.Damo.Service.Command.VehicleCommandManager;
import com.MyParkingLot.Damo.Service.websocket.WebSocketService;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingService;
import com.MyParkingLot.Damo.Service.time.TimeManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParkingServiceFactory {
    private final ParkingService parkingService;
    private final VehicleFactory vehicleFactory;
    private final VehicleRepository vehicleRepository;
    private final TimeManager timeManager;
    private final VehicleCommandManager vehicleCommandManager;
    private final WebSocketService webSocketService;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;

    public  ParkingSpace  assignSpace(Vehicle vehicle){
        //選擇聽車廠停入
        List<ParkingLot> parkingLotList = parkingLotRepository.findAll();
        ParkingLot parkingLot = randomParkingLot(parkingLotList);
        vehicle.setParkingLot(parkingLot);
        //排查停車場是否有空位
        List<ParkingSpace> spaces = parkingLot.getParkingSpaceList();
        List<ParkingSpace> availableSpaces = spaces
                .stream().filter(space -> !space.isOccupied())
                .toList();
        if (availableSpaces.isEmpty()) throw new APIException("沒有可用的停車位");
        Random random = new Random();
        ParkingSpace parkingSpace = availableSpaces.get(random.nextInt( availableSpaces.size()));
        //ParkingSpace parkingSpace = new ParkingSpace();
        vehicle.assignParkingSpace(parkingSpace);

        log.info("車輛 {} 被分配至停車場 {} 的車位 {} (type: {})",
                vehicle.getLicense(),
                parkingLot.getParkingLotName(),
                parkingSpace.getParkingSpaceId(),
                parkingSpace.getParkingSpaceType());

        return parkingSpace;
    }


    //隨機抽取停車場(考慮抽出擴展
    private ParkingLot randomParkingLot(List<ParkingLot> parkingLots) {
        Random random = new Random();
        if (parkingLots == null) throw new APIException("停車場未創建");
        int index = random.nextInt(parkingLots.size());
        return parkingLots.get(index);
    }



    //自動指派
    public void autoAssignVehicle(){
        Vehicle vehicle = vehicleFactory.generateVehicle();
        //parkingService.vehicleEntering(vehicle);

        vehicleCommandManager.addCommand(new EnterVehicleCommand(vehicle,parkingService,webSocketService));
    }

    //自動離場，從"所有車庫裡判斷"
    public void autoLeaveVehicles(Long id){
        List<Vehicle> vehicles = vehicleRepository.findAll();
        LocalDateTime now = timeManager.getCurrentGameTime();

        for (Vehicle vehicle : vehicles){
            if (vehicle.getExpectedVehicleLeaveTime() != null &&
                    vehicle.getActualLeaveTime() == null) {

                LocalDateTime expected = vehicle.getExpectedVehicleLeaveTime();

                if (now.isAfter(expected)) {
                    //以命令模式進行離場
                    vehicleCommandManager.addCommand(new LeaveVehicleCommand(vehicle,parkingService,webSocketService));
                    log.info("自動離場成功：{}", vehicle.getLicense());
                }
            }

        }
    }



}
