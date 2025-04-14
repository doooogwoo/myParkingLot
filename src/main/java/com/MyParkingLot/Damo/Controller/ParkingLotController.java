package com.MyParkingLot.Damo.Controller;

import com.MyParkingLot.Damo.Payload.dto.ParkingLotDto;
import com.MyParkingLot.Damo.Service.logic.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parkingLot")
public class ParkingLotController {
    private final ParkingLotService parkingLotService;
    @Autowired
    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PostMapping("/init-parkingLot")
    public ResponseEntity<String> initParkingLot(@RequestParam String parkingLotName){
        parkingLotService.initParkingLot(parkingLotName);
        return new ResponseEntity<String> (parkingLotName,HttpStatus.CREATED);
    }

    @PostMapping("/add-parkingLot")
    public ResponseEntity<String> addParkingLot(@RequestParam String parkingLotName,
                                                @RequestParam int ticketFee){
       parkingLotService.buildParkingLot(parkingLotName,ticketFee);
       String message = "build success" ;
        return new ResponseEntity<String> (message,HttpStatus.CREATED);
    }

    @GetMapping("/getInfoBy/{id}")
    public ResponseEntity<ParkingLotDto> getParkingLotInfoById(@PathVariable Long id){
        ParkingLotDto parkingLotDto = parkingLotService.getParkingLotInfoById(id);
        return new  ResponseEntity<ParkingLotDto>(parkingLotDto,HttpStatus.OK);
    }
}
