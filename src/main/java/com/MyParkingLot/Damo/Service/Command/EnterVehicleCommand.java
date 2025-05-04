package com.MyParkingLot.Damo.Service.Command;

import com.MyParkingLot.Damo.Repository.VehicleRepository;
//import com.MyParkingLot.Damo.Service.observer.ParkingLotIncome;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.Service.websocket.WebSocketService;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class EnterVehicleCommand implements VehicleCommand{

    //接收者 -->parkingspace
    //動作目標 -->vhicle
    //具體邏輯 -->入場
    private final Vehicle vehicle;
    private final ParkingService parkingService;
    //private final WebSocketService webSocketService;
    private final VehicleRepository vehicleRepository;
    public EnterVehicleCommand(Vehicle vehicle, ParkingService parkingService, //WebSocketService webSocketService,
                               VehicleRepository vehicleRepository) {
        this.vehicle = vehicle;
        this.parkingService = parkingService;
        //this.webSocketService = webSocketService;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void execute() {
        vehicleRepository.save(vehicle);
        parkingService.vehicleEntering(vehicle);
        Long lotId = vehicle.getParkingLot().getParkingLotId();
        //webSocketService.sendParkingLotSpaceUpdate(lotId);
    }

    @Override
    public String getDescription() {
        return "進場指令- 車牌:" + vehicle.getLicense();
    }
}
