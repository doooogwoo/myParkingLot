package com.MyParkingLot.Damo.Service.logic;

import com.MyParkingLot.Damo.Exception.APIException;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.Payload.dto.ParkingLotDto;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Service.factory.ParkingLotFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {
    private final ParkingLotFactory parkingLotFactory;
    private final ModelMapper mapper;
    private final ParkingLotRepository parkingLotRepository;

    @Override
    public void initParkingLot(String parkinglotName){
        parkingLotFactory.initParkingLot(parkinglotName);
    }

    @Override
    public void buildParkingLot(String parkingname, int ticketFee){
        parkingLotFactory.createParkingLot(parkingname,ticketFee);
    }

    @Override
    public ParkingLotDto getParkingLotInfoById(Long id) {
        ParkingLot parkingLot = parkingLotRepository.findById(id)
                .orElseThrow(() -> new APIException("找不到停車場"));
        ParkingLotDto parkingLotDto = mapper.map(parkingLot,ParkingLotDto.class);
        return parkingLotDto;
    }
}
