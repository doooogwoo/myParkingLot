package com.MyParkingLot.Damo.domain.Model;

import com.MyParkingLot.Damo.Exception.APIException;
import com.MyParkingLot.Damo.Service.observer.ParkingObserver;
import com.MyParkingLot.Damo.Service.observer.VehicleEvent;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ParkingLot implements ParkingObserver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkingLotId;
    private String parkingLotName;

    private LocalDateTime createAt;

    private int capacity;
    private int income = 0; //收入
    private int expenses; //維修費用
    private int floors;

    private String events;
    private boolean isFull;
    private boolean isHandicapFull;
    private boolean isElectricFull;
    private double handicapBonus;

    @OneToMany(mappedBy = "parkingLot",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ParkingSpace> parkingSpaceList = new ArrayList<>();

    @OneToOne(mappedBy = "parkingLot",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ParkingTicket parkingTicket;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Player player;

    @OneToMany(mappedBy = "parkingLot",cascade = {CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<WeeklyReport> weeklyReports = new ArrayList<>();

    public void addIncome(int amount){
        if (amount <0){
            throw new APIException("停車費不能為負!!");
        }
        this.income += amount;
    }

    @Override
    public void update(VehicleEvent event) {
        addIncome(event.getIncome());
    }
}
