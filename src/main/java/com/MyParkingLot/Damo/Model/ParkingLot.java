package com.MyParkingLot.Damo.Model;

import jakarta.persistence.*;
import lombok.*;

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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ParkingSpace> parkingSpaceList = new ArrayList<>();

    @OneToOne(mappedBy = "parkingLot")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ParkingTicket parkingTicket;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Player player;
}
