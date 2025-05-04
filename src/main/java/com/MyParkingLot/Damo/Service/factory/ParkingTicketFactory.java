package com.MyParkingLot.Damo.Service.factory;

import com.MyParkingLot.Damo.Exception.ResourceNotFoundException;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.ParkingTicket;
import com.MyParkingLot.Damo.Repository.ParkingTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParkingTicketFactory {
    private final ParkingTicketRepository parkingTicketRepository;

    public ParkingTicket generateTicket(ParkingLot parkingLot){
        ParkingTicket parkingTicket = new ParkingTicket();
        parkingTicket.setRate(30);
        parkingTicket.assignParkingLot(parkingLot);
        parkingTicketRepository.save(parkingTicket);
        return parkingTicket;
    }

    public void setFee(Long parkingTicketId,int fee){
        ParkingTicket ticket = parkingTicketRepository.findById(parkingTicketId)
                .orElseThrow(() -> new ResourceNotFoundException("parkingTicket","parkingTicketId",parkingTicketId));
        ticket.setRate(fee);
        parkingTicketRepository.save(ticket);
    }

    public int getFee(Long parkingTicketId){
        ParkingTicket ticket = parkingTicketRepository.findById(parkingTicketId)
                .orElseThrow(() -> new ResourceNotFoundException("parkingTicket","parkingTicketId",parkingTicketId));
        return ticket.getRate();
    }

    public ParkingTicket getGenerateTicket(ParkingLot lot) {
        ParkingTicket ticket = new ParkingTicket();
        ticket.setRate(30);
        ticket.assignParkingLot(lot);
        return parkingTicketRepository.save(ticket);
    }

}
