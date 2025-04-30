package com.MyParkingLot.Damo.Controller;


import com.MyParkingLot.Damo.Payload.dto.VehicleDto;
import com.MyParkingLot.Damo.Service.logic.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    @GetMapping("/generateVehicle")
    public ResponseEntity<VehicleDto> generateVehicle(){
        VehicleDto vehicleDto = vehicleService.addVehicle();
        return new ResponseEntity<>(vehicleDto,HttpStatus.OK);
    }
}
