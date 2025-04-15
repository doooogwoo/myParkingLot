package com.MyParkingLot.Damo.domain.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    @Column(nullable = false, unique = true)
    private String license;

    @Column(name = "is_handicapped", nullable = false)
    private boolean isHandicapped;

    @Column(name = "is_electric_vehicle", nullable = false)
    private boolean isElectricVehicle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    private LocalDateTime vehicleEnterTime; //-->入場
    private Duration parkingDuration;//停多久
    //private LocalDateTime expectedVehicleLeaveTime;//預計出場時間
    private LocalDateTime actualLeaveTime;//實際離場時間

    private int fee; // 實際收費，單位：元（由系統記錄收費後金額）


    // 非擁有方（由 ParkingSpace 控制關聯）
    @OneToOne(mappedBy = "vehicle")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ParkingSpace parkingSpace;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ParkingLot parkingLot;


    public void assignParkingSpace(ParkingSpace parkingSpace){
        this.setParkingSpace(parkingSpace);
        parkingSpace.setVehicle(this);
    }
}