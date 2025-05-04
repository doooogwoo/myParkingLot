package com.MyParkingLot.Damo.Service.logic.location;

import com.MyParkingLot.Damo.Payload.dto.location.LocationMapDto;
import org.springframework.stereotype.Component;

import java.util.List;


public interface LocationService {

    List<LocationMapDto> getLocationInfo();
}
