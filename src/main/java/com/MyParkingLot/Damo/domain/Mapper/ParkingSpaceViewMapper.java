package com.MyParkingLot.Damo.domain.Mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import com.MyParkingLot.Damo.Payload.dto.ParkingSpaceViewDto;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
@Component
@RequiredArgsConstructor
public class ParkingSpaceViewMapper {

    private final ParkingSpaceDtoMapper dtoMapper;

    public ParkingSpaceViewDto toViewDto(ParkingSpace space) {
        ParkingSpaceViewDto viewDto = new ParkingSpaceViewDto();
        viewDto.setParkingSpaceId(space.getParkingSpaceId());
        viewDto.setOccupied(space.isOccupied());
        viewDto.setSymbol(dtoMapper.generateSymbol(space));

        if (space.isOccupied() && space.getVehicle() != null) {
            viewDto.setLicenseCode(space.getVehicle().getLicense());
        }

        return viewDto;
    }
}
