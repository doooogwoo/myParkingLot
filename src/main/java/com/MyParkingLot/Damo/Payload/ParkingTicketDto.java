package com.MyParkingLot.Damo.Payload;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ParkingTicketDto {
    private Long parkingTicketId;
    private int fee;
    private double discount;
}