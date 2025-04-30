package com.MyParkingLot.Damo.Service.logic;

import com.MyParkingLot.Damo.Exception.APIException;
import com.MyParkingLot.Damo.Exception.ResourceNotFoundException;
import com.MyParkingLot.Damo.Payload.dto.PlayerDto;
import com.MyParkingLot.Damo.Repository.PlayerRepository;
import com.MyParkingLot.Damo.Service.factory.ParkingLotFactory;
import com.MyParkingLot.Damo.Service.factory.PlayerFactory;
import com.MyParkingLot.Damo.domain.Model.Player;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PlayerServiceImpl implements PlayerService{
    private final PlayerRepository playerRepository;
    private final PlayerFactory playerFactory;
    private final  ResetService resetService;
    //private final ModelMapper mapper;
    @Override
    @Transactional
    public void initPlayer(String name) {
        // 先確認有沒有玩家，不重複新增
        boolean playerExists = playerRepository.existsById(1);
        if (playerExists) {
            System.out.println("⚠️ 玩家已存在，不再初始化！");
            throw new APIException("玩家已經存在!!！");
        }
        resetService.resetGameData();
        playerFactory.initPlayer(name);
    }

    @Override
    public PlayerDto getPlayerInfo(){
        Player player = playerRepository.findById(1)
                .orElseThrow(() ->new ResourceNotFoundException("找不到","PlayerId:",1));
        int parkinglotNum = player.getOwnedParkingLots().size();
        PlayerDto dto = new PlayerDto();
        dto.setPlayerName(player.getPlayerName());
        dto.setParkingLotTotal(parkinglotNum);
        dto.setBalance(player.getBalance());
        return dto;
    }
}
