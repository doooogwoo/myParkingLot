package com.MyParkingLot.Damo.domain.Model;

import com.MyParkingLot.Damo.Exception.BusinessException;
import com.MyParkingLot.Damo.Exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Slf4j
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
    private int floor;


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

    @Override
    public String toString() {
        return "Space[" + parkingSpaceId + ", " + parkingSpaceType + ", floor=" + floor + ", occupied=" + isOccupied + "]";
    }


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
        log.info("🚗 車輛 {} 進入車位 {} (樓層 {}, 類型 {})", vehicle.getLicense(), this.parkingSpaceId, this.floor, this.parkingSpaceType);
        if (this.getParkingLot() != null) {
            vehicle.setParkingLot(this.getParkingLot());
        }

    }

    public void unassignVehicle(){
        if (!this.isOccupied() || this.vehicle == null) {
            throw new BusinessException(ErrorCode.VEHICLE_LEAVE_SPACE_IS_EMPTY);
        }

        Vehicle v = this.vehicle;
        this.setOccupied(false);
        this.vehicle.setParkingSpace(null);
        this.setVehicle(null);
        log.info("🚗 車輛 {} 離開車位 {} (樓層 {})", v.getLicense(), this.parkingSpaceId, this.floor);
    }

}
