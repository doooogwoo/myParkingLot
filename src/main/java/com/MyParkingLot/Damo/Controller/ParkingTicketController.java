package com.MyParkingLot.Damo.Controller;

import com.MyParkingLot.Damo.Payload.dto.ParkingTicketDto;
import com.MyParkingLot.Damo.Service.logic.ParkingTicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parkingTicket")
public class ParkingTicketController {
    private final ParkingTicketService ticketService;
    @Autowired
    public ParkingTicketController(ParkingTicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PatchMapping("/{ticketId}/updateTicket")
    public ResponseEntity<String> updateFee(@PathVariable Long ticketId,
                                            @Valid @RequestBody ParkingTicketDto parkingTicketDto){
        ParkingTicketDto ticketDto = ticketService.updateFee(ticketId,parkingTicketDto.getFee());
        return new ResponseEntity<String>("票價更新成功:" + ticketDto.getFee(), HttpStatus.OK);
    }
}
