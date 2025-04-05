package com.MyParkingLot.Damo.Service.parkingTicket;

import com.MyParkingLot.Damo.Factory.ParkingTicketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
@Service
public class ParkingTicketServiceImpl implements ParkingTicketService{
   private final ParkingTicketFactory ticketGenerate;
   @Autowired
    public ParkingTicketServiceImpl(ParkingTicketFactory parkingTicketFactory) {
        this.ticketGenerate = parkingTicketFactory;
    }

    @Override
    public int calculateFee(Duration duration, Long parkingTicketId) {
        //int hours = Math.max(1, (int) duration.toHours()); // 最低收費一小時
        int hours = (int) duration.toHours();
        return hours * ticketGenerate.getFee(parkingTicketId);
    }
}
