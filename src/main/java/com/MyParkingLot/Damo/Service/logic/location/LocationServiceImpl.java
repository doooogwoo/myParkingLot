package com.MyParkingLot.Damo.Service.logic.location;

import com.MyParkingLot.Damo.Exception.ResourceNotFoundException;
import com.MyParkingLot.Damo.Payload.dto.ParkingLotDto;
import com.MyParkingLot.Damo.Payload.dto.location.LocationInfo;
import com.MyParkingLot.Damo.Payload.dto.location.LocationMapDto;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{
    private final CityLocationManager cityLocationManager;
    private final ParkingLotRepository parkingLotRepository;
    private final ModelMapper mapper;

    @Override
    public List<LocationMapDto> getLocationInfo(){
        return cityLocationManager.getAllLocations();
    }

    @Override
    public LocationMapDto getLotName(String id) {
        ParkingLot lot = parkingLotRepository.findByLocationId(id)
                .orElseThrow(() ->new ResourceNotFoundException("parkingLot","parkingNameByLocationId",id));
        ParkingLotDto dto = mapper.map(lot,ParkingLotDto.class);
        LocationInfo info = cityLocationManager.getLocationInfo(id);
        LocationMapDto mapDto = mapper.map(info,LocationMapDto.class);
        mapDto.setParkingLotName(dto.getParkingLotName());
        mapDto.setLotId(dto.getLocationId());
        return mapDto;
    }
}
