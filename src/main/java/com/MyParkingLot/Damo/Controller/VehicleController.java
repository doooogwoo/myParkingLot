package com.MyParkingLot.Damo.Controller;


import com.MyParkingLot.Damo.Payload.VehicleDto;
import com.MyParkingLot.Damo.Service.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vehicle")
public class VehicleController {
    private final VehicleService vehicleService;
    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
    @GetMapping("generateVehicle")
    public ResponseEntity<VehicleDto> generateVehicle(){
        VehicleDto vehicleDto = vehicleService.addVehicle();
        return new ResponseEntity<>(vehicleDto,HttpStatus.OK);
    }
}
