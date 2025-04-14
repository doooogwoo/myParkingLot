package com.MyParkingLot.Damo.domain.Mapper;

import com.MyParkingLot.Damo.Payload.dto.ParkingSpaceDto;
import com.MyParkingLot.Damo.Payload.dto.VehicleDto;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParkingSpaceDtoMapper {
    private final ModelMapper mapper;

    public ParkingSpaceDto toDto(ParkingSpace space) {
        if (space == null) return null;

        ParkingSpaceDto spaceDto = new ParkingSpaceDto();
        spaceDto.setParkingSpaceId(space.getParkingSpaceId());
        spaceDto.setSpaceIncome(space.getSpaceIncome());
        spaceDto.setOccupied(space.isOccupied());

        if (space.getVehicle() != null) {
            spaceDto.setVehicleDto(toVehicleDto(space.getVehicle()));
        }

        if (space.getParkingLot() != null) {
            spaceDto.setParkingLotDtoId(space.getParkingLot().getParkingLotId());
        }

        spaceDto.setSymbol(generateSymbol(space));

        return spaceDto;
    }

    private VehicleDto toVehicleDto(Vehicle vehicle) {
        return mapper.map(vehicle, VehicleDto.class);
    }

    public String generateSymbol(ParkingSpace space) {
        if (space == null) return "";

        return switch (space.getParkingSpaceType()) {
            case ElectricParkingSpace -> "E";
            case HandicappedParkingSpace -> "H";
            default -> "";
        };
    }
}
