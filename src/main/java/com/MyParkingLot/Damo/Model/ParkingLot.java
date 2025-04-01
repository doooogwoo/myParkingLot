package com.MyParkingLot.Damo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkingLotId;
    private String parkingLotName;
    private int capacity;
    private int income; //收入
    private int expenses; //維修費用
    private int floors;

    private String events;
    private boolean isFull;
    private boolean isHandicapFull;
    private boolean isElectricFull;
    private double handicapBonus;

    @OneToMany(mappedBy = "parkingLot")
    private List<ParkingSpace> parkingSpaceList = new ArrayList<>();

    @OneToOne(mappedBy = "parkingLot")
    private ParkingTicket parkingTicket;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
}
