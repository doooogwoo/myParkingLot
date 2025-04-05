package com.MyParkingLot.Damo.Model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ParkingSpace {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long parkingSpaceId;

    @Enumerated(EnumType.STRING)
    private ParkingSpaceType parkingSpaceType;

    @Getter
    @Setter
    private boolean isOccupied;

    private int spaceIncome;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "parkingLot_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ParkingLot parkingLot;
}
