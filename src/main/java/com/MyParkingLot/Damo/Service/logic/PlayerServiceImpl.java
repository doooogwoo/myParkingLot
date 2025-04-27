package com.MyParkingLot.Damo.Service.logic;

import com.MyParkingLot.Damo.Exception.ResourceNotFoundException;
import com.MyParkingLot.Damo.Payload.dto.PlayerDto;
import com.MyParkingLot.Damo.Repository.PlayerRepository;
import com.MyParkingLot.Damo.domain.Model.Player;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PlayerServiceImpl implements PlayerService{
    private final PlayerRepository playerRepository;
    @Override
    public PlayerDto getPlayerInfo(){
        Player player = playerRepository.findById(1)
                .orElseThrow(() ->new ResourceNotFoundException("找不到","PlayerId:",1));
        int parkinglotNum = player.getOwnedParkingLots().size();
        PlayerDto dto = new PlayerDto();
        dto.setPakingLotTotal(parkinglotNum);
        dto.setBalance(player.getBalance());
        return dto;
    }
}
