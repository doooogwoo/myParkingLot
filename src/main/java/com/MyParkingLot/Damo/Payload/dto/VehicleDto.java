package com.MyParkingLot.Damo.Payload.dto;

import com.MyParkingLot.Damo.domain.Model.VehicleType;
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
    private LocalDateTime expectedVehicleLeaveTime;//預計出場時間
    private LocalDateTime actualLeaveTime;//實際離場時間

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
