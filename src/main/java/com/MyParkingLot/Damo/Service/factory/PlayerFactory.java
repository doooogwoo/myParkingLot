package com.MyParkingLot.Damo.Service.factory;

import com.MyParkingLot.Damo.Exception.ResourceNotFoundException;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Repository.PlayerRepository;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlayerFactory {
    private final PlayerRepository playerRepository;
    private final ParkingLotFactory parkingLotFactory;
    private final ParkingLotRepository parkingLotRepository;

    public Player initPlayer(String name) {
        Player player = new Player();
        player.setPlayerName(name);
        player.setPlayerId(1);
        player.setBalance(10000);
        Player savedPlayer = playerRepository.save(player);

        ParkingLot parkingLot = parkingLotFactory.initParkingLot("NeoPark Systems 7G");
        parkingLot.setPlayer(savedPlayer);
        ParkingLot saveLot = parkingLotRepository.save(parkingLot);


        List<ParkingLot> lots = new ArrayList<>();
        lots.add(saveLot);
        savedPlayer.setOwnedParkingLots(lots);

        playerRepository.save(savedPlayer);
        return savedPlayer;
    }
}
