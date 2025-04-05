package com.MyParkingLot.Damo.Factory;

import com.MyParkingLot.Damo.Exception.ResourceNotFoundException;
import com.MyParkingLot.Damo.Model.ParkingLot;
import com.MyParkingLot.Damo.Model.ParkingTicket;
import com.MyParkingLot.Damo.Repository.ParkingTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingTicketFactory {
    private final ParkingTicketRepository parkingTicketRepository;
    @Autowired
    public ParkingTicketFactory(ParkingTicketRepository parkingTicketRepository) {
        this.parkingTicketRepository = parkingTicketRepository;
    }


    public void generateTicket(ParkingLot parkingLot){
        ParkingTicket parkingTicket = new ParkingTicket();
        parkingTicket.setFee(30);
        parkingTicket.assignParkingLot(parkingLot);
        parkingTicketRepository.save(parkingTicket);
    }

    public void setFee(Long parkingTicketId,int fee){
        ParkingTicket ticket = parkingTicketRepository.findById(parkingTicketId)
                .orElseThrow(() -> new ResourceNotFoundException("parkingTicket","parkingTicketId",parkingTicketId));
        ticket.setFee(fee);
        parkingTicketRepository.save(ticket);
    }

    public int getFee(Long parkingTicketId){
        ParkingTicket ticket = parkingTicketRepository.findById(parkingTicketId)
                .orElseThrow(() -> new ResourceNotFoundException("parkingTicket","parkingTicketId",parkingTicketId));
        return ticket.getFee();
    }

    public ParkingTicket getGenerateTicket(ParkingLot lot) {
        ParkingTicket ticket = new ParkingTicket();
        ticket.setFee(30);
        ticket.assignParkingLot(lot);
        return parkingTicketRepository.save(ticket);
    }

}
