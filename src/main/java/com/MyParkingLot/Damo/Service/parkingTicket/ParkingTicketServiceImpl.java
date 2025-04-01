package com.MyParkingLot.Damo.Service.parkingTicket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
@Service
public class ParkingTicketServiceImpl implements ParkingTicketService{
   private final ParkingTicketGenerate ticketGenerate;
   @Autowired
    public ParkingTicketServiceImpl(ParkingTicketGenerate parkingTicketGenerate) {
        this.ticketGenerate = parkingTicketGenerate;
    }

    @Override
    public int calculateFee(Duration duration, Long parkingTicketId) {
        //int hours = Math.max(1, (int) duration.toHours()); // 最低收費一小時
        int hours = (int) duration.toHours();
        return hours * ticketGenerate.getFee(parkingTicketId);
    }
}
