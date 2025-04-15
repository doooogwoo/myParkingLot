package com.MyParkingLot.Damo.Controller;

import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.Service.Command.EnterVehicleCommand;
import com.MyParkingLot.Damo.Service.Command.VehicleCommand;
import com.MyParkingLot.Damo.Service.Command.VehicleCommandManager;
import com.MyParkingLot.Damo.Service.websocket.WebSocketService;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingService;
import com.MyParkingLot.Damo.Service.factory.VehicleFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/commands")
public class CommandController {
    private final VehicleCommandManager commandManager;
    private final VehicleFactory vehicleFactory;
    private final ParkingService parkingService;
    private final WebSocketService webSocketService;
    private final VehicleRepository vehicleRepository;

    @PostMapping("/enter")
    public ResponseEntity<String> enterVehicleCommand() {
        Vehicle vehicle = vehicleFactory.generateVehicle();
        VehicleCommand vehicleCommand = new EnterVehicleCommand(vehicle, parkingService,webSocketService,vehicleRepository);
        commandManager.addCommand(vehicleCommand);
        return new ResponseEntity<String>("已加入進場指令", HttpStatus.OK);
    }

    @PostMapping("/runAll")
    public ResponseEntity<String> enterVehicleRunAll() {
        commandManager.runAll();
        return new ResponseEntity<String>("已執行所有指令", HttpStatus.OK);
    }

    @PostMapping("/runOne")
    public ResponseEntity<String> enterVehicleRunOne() {
        commandManager.runOne();
        return new ResponseEntity<String>("已執行一筆指令", HttpStatus.OK);
    }
}
