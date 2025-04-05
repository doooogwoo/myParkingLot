package com.MyParkingLot.Damo.Payload;

import com.MyParkingLot.Damo.Model.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {
    private Long vehicleId;
    private String license;
    private boolean isHandicapped;
    private boolean electricVehicle;

    private LocalDateTime vehicleEnterTime; //-->入場
    private Duration parkingDuration;//停多久
    private LocalDateTime vehicleLeaveTime;//出場

    private VehicleType vehicleType;

    private Long parkingSpaceDtoId;

    @Override
    public String toString() {
        return "VehicleDto{" +
                "license='" + license + '\'' +
                ", vehicleType=" + vehicleType +
                ", handicapped=" + isHandicapped +
                ", electricVehicle=" + electricVehicle +
                '}';
    }
}
