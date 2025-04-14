package com.MyParkingLot.Damo.Service.Command;

import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.Service.websocket.WebSocketService;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LeaveVehicleCommand implements VehicleCommand{
    //接收者 -->parkingspace
    //動作目標 -->vhicle
    //具體邏輯 -->入場
    private  final Vehicle vehicle;
    private final ParkingService parkingService;
    private final WebSocketService webSocketService;
    public LeaveVehicleCommand(Vehicle vehicle, ParkingService parkingService, WebSocketService webSocketService) {
        this.vehicle = vehicle;
        this.parkingService = parkingService;
        this.webSocketService = webSocketService;
    }

    @Override
    public void execute() {
        Long lotId = vehicle.getParkingLot().getParkingLotId();//因維離場會變null，要先存起來
        parkingService.vehicleLeaving(vehicle.getVehicleId());
        webSocketService.sendParkingLotSpaceUpdate(lotId);
        log.info("🔔 WebSocket 正在推播車位更新...");

    }

    @Override
    public String getDescription() {
        return "出場指令 - 車牌：" + vehicle.getLicense();
    }
}
