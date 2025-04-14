package com.MyParkingLot.Damo.Service.logic;

import com.MyParkingLot.Damo.Exception.APIException;
import com.MyParkingLot.Damo.Payload.dto.ParkingSpaceViewDto;
import com.MyParkingLot.Damo.domain.Mapper.ParkingSpaceViewMapper;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.Payload.dto.ParkingSpaceDto;
import com.MyParkingLot.Damo.Payload.dto.VehicleDto;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpaceViewMapper mapper;
   @Override
    public List<ParkingSpaceViewDto> getSpaceStatusList(Long parkingLotId){
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(()->new APIException("ParkingSpaceServiceImpl找不到停車場"));
        List<ParkingSpaceViewDto> spaces = parkingLot.getParkingSpaceList()
                .stream().map(mapper::toViewDto).toList();
        return spaces;
    }
}
