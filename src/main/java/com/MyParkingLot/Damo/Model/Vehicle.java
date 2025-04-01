package com.MyParkingLot.Damo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private boolean handicapped;

    @Column(name = "is_electric_vehicle", nullable = false)
    private boolean electricVehicle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    private LocalDateTime vehicleEnterTime; //-->入場
    private Duration parkingDuration;//停多久
    private LocalDateTime vehicleLeaveTime;//出場

    // 非擁有方（由 ParkingSpace 控制關聯）
    @OneToOne(mappedBy = "vehicle")
    private ParkingSpace parkingSpace;


    public void assignParkingSpace(ParkingSpace parkingSpace){
        this.setParkingSpace(parkingSpace);
        parkingSpace.setVehicle(this);
    }
}