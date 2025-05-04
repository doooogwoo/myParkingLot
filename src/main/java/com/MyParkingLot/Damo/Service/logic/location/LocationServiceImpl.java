package com.MyParkingLot.Damo.Service.logic.location;

import com.MyParkingLot.Damo.Payload.dto.location.LocationMapDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{
    private final CityLocationManager cityLocationManager;

    @Override
    public List<LocationMapDto> getLocationInfo(){
        return cityLocationManager.getAllLocations();
    }
}
