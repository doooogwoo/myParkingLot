package com.MyParkingLot.Damo.Service.logic;

import com.MyParkingLot.Damo.Payload.dto.ParkingTicketDto;

import java.time.Duration;


public interface ParkingTicketService {
    int calculateFee(Duration duration, Long parkingTicketId);

    ParkingTicketDto updateFee(Long ticketId, int fee);
}