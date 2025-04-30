package com.MyParkingLot.Damo.Controller;

import com.MyParkingLot.Damo.Payload.dto.PlayerDto;
import com.MyParkingLot.Damo.Service.logic.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    @GetMapping("/getInfo")
    public ResponseEntity<PlayerDto> playerInfo(){
        PlayerDto playerDto = playerService.getPlayerInfo();
        return new  ResponseEntity<>(playerDto, HttpStatus.OK);
    }

    @PostMapping("/initPlayer")
    public ResponseEntity<String> initPlayer(@RequestParam String playerName){
        playerService.initPlayer(playerName);
        String massage = "Player" + playerName +" init success!";
        return new  ResponseEntity<>(massage, HttpStatus.CREATED);
    }
}
