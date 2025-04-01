package com.MyParkingLot.Damo.Service.parkingTicket;

import org.springframework.stereotype.Component;

import java.time.Duration;


public interface ParkingTicketService {
    int calculateFee(Duration duration, Long parkingTicketId);
}