package com.MyParkingLot.Damo.Service.factory;

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
                    //parkingService.vehicleLeaving(vehicle.getVehicleId());
                    //可以直接用以下替代?


                    //以命令模式進行離場
                    vehicleCommandManager.addCommand(new LeaveVehicleCommand(vehicle,parkingService,webSocketService));
                    log.info("自動離場成功：{}", vehicle.getLicense());
                }
            }

        }
    }
}
