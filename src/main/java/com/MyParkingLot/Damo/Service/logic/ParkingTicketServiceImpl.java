package com.MyParkingLot.Damo.Service.logic;

import com.MyParkingLot.Damo.Exception.ResourceNotFoundException;
import com.MyParkingLot.Damo.Service.factory.ParkingTicketFactory;
import com.MyParkingLot.Damo.domain.Model.ParkingTicket;
import com.MyParkingLot.Damo.Payload.dto.ParkingTicketDto;
import com.MyParkingLot.Damo.Repository.ParkingTicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ParkingTicketServiceImpl implements ParkingTicketService {
    private final ParkingTicketFactory ticketGenerate;
    private final ParkingTicketRepository parkingTicketRepository;
    private final ModelMapper mapper;

    @Autowired
    public ParkingTicketServiceImpl(ParkingTicketFactory parkingTicketFactory, ParkingTicketRepository parkingTicketRepository, ModelMapper mapper) {
        this.ticketGenerate = parkingTicketFactory;
        this.parkingTicketRepository = parkingTicketRepository;
        this.mapper = mapper;
    }

    @Override
    public int calculateFee(Duration duration, Long parkingTicketId) {
        //int hours = Math.max(1, (int) duration.toHours()); // 最低收費一小時
        int hours = (int) duration.toHours();
        return hours * ticketGenerate.getFee(parkingTicketId);
    }

    @Override
    public ParkingTicketDto updateFee(Long ticketId, int rate) {
        ParkingTicket parkingTicket = parkingTicketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("parkingTicket找不到 ","parkingTicketId:",ticketId));
        parkingTicket.setRate(rate);
        parkingTicketRepository.save(parkingTicket);
        return mapper.map(parkingTicket,ParkingTicketDto.class);
    }
}
