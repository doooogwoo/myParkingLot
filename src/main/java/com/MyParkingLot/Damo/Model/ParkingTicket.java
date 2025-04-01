package com.MyParkingLot.Damo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingTicket {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long parkingTicketId;

    @Min(10)
    @Max(60)
    private int fee;

    private double discount;

    @OneToOne
    @JoinColumn(name = "parkingLot_id")
    private ParkingLot parkingLot;

    public void assignParkingLot(ParkingLot parkingLot){
        this.parkingLot = parkingLot;
        parkingLot.setParkingTicket(this);
    }
}
