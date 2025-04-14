package com.MyParkingLot.Damo.Service.Command;

import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.Service.websocket.WebSocketService;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnterVehicleCommand implements VehicleCommand{

    //接收者 -->parkingspace
    //動作目標 -->vhicle
    //具體邏輯 -->入場
    private final Vehicle vehicle;
    private final ParkingService parkingService;
    private final WebSocketService webSocketService;
    public EnterVehicleCommand(Vehicle vehicle,
                               ParkingService parkingService,
                               WebSocketService webSocketService) {
        this.vehicle = vehicle;
        this.parkingService = parkingService;
        this.webSocketService = webSocketService;
    }
    @Override
    public void execute() {
        parkingService.vehicleEntering(vehicle);
        Long lotId = vehicle.getParkingLot().getParkingLotId();
        webSocketService.sendParkingLotSpaceUpdate(lotId);

    }

    @Override
    public String getDescription() {
        return "進場指令- 車牌:" + vehicle.getLicense();
    }
}
