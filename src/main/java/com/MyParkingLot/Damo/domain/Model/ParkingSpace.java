package com.MyParkingLot.Damo.domain.Model;

import com.MyParkingLot.Damo.Exception.BusinessException;
import com.MyParkingLot.Damo.Exception.ErrorCode;
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

    public void assignVehicle(Vehicle vehicle) {
        if (this.isOccupied()) {
            throw new BusinessException(ErrorCode.SPACE_ALREADY_OCCUPIED);
        }

        if (this.parkingSpaceType == ParkingSpaceType.HandicappedParkingSpace && !vehicle.isHandicapped()) {
            throw new BusinessException(ErrorCode.VEHICLE_ENTER_NOT_Handicapped);
        }

        if (this.parkingSpaceType == ParkingSpaceType.ElectricParkingSpace && !vehicle.isElectricVehicle()) {
            throw new BusinessException(ErrorCode.VEHICLE_ENTER_NOT_Electric);
        }

        this.setOccupied(true);
        this.setVehicle(vehicle);
        vehicle.setParkingSpace(this);
    }

    public void unassignVehicle(){
        if (!this.isOccupied() || this.vehicle == null) {
            throw new BusinessException(ErrorCode.VEHICLE_LEAVE_SPACE_IS_EMPTY);
        }

        this.setOccupied(false);
        this.vehicle.setParkingSpace(null);
        this.setVehicle(null);
    }

}
