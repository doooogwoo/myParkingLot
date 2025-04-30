package com.MyParkingLot.Damo.Service.logic;

import com.MyParkingLot.Damo.Payload.dto.PlayerDto;

public interface PlayerService {

    void initPlayer(String name);

    PlayerDto getPlayerInfo();
}
