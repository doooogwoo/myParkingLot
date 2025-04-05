package com.MyParkingLot.Damo.Service.Command;

import com.MyParkingLot.Damo.Model.Vehicle;
import com.MyParkingLot.Damo.Service.parkingService.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;

public class LeaveVehicleCommand implements VehicleCommand{
    //接收者 -->parkingspace
    //動作目標 -->vhicle
    //具體邏輯 -->入場
    private  final Vehicle vehicle;
    private final ParkingService parkingService;
    @Autowired
    public LeaveVehicleCommand(Vehicle vehicle, ParkingService parkingService) {
        this.vehicle = vehicle;
        this.parkingService = parkingService;
    }

    @Override
    public void execute() {
        parkingService.vehicleLeaving(vehicle.getVehicleId());
    }

    @Override
    public String getDescription() {
        return "出場指令 - 車牌：" + vehicle.getLicense();
    }
}
