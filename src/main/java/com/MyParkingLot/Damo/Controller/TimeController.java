package com.MyParkingLot.Damo.Controller;

import com.MyParkingLot.Damo.Payload.dto.TimeDto;
import com.MyParkingLot.Damo.Service.GameScheduler.VehicleGameScheduler;
import com.MyParkingLot.Damo.Service.logic.ResetService;
import com.MyParkingLot.Damo.Service.time.TimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/time")
@RequiredArgsConstructor
public class TimeController {
    private final TimeService timeService;
    private final ResetService resetService;

    @GetMapping("/getCurrentTime") //--->查詢當前時間
    public ResponseEntity<String> getCurrentTime() {
        String timeDtoNow = timeService.getFormattedCurrentGameTime();
        return new ResponseEntity<>(timeDtoNow, HttpStatus.OK);
    }

    @PostMapping("/saveTimeRecord") //-->存檔
    public ResponseEntity<TimeDto> saveTime() {
        TimeDto time = timeService.saveGameTime();
        return new ResponseEntity<>(time, HttpStatus.OK);
    }


    @PostMapping("/startGame") //---->開始遊戲
    public ResponseEntity<String> startGame() {
//        TimeDto startTimeDto = timeService.startGame();
//        vehicleGameScheduler.setRunning(true);
        timeService.resumeGame();
        String timeDtoNow = timeService.getFormattedCurrentGameTime();
        String message =
                "<遊戲開始> :  時間與車輛模擬已開始。目前時間: " + timeDtoNow;
        log.info("遊戲開始!!!!!!!!!");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/saveAndExit") //-->存檔並結束遊戲
    public ResponseEntity<String> saveAndExit() {
//        TimeDto time = timeService.saveGameTime();
//        vehicleGameScheduler.setRunning(false);
        String timeDtoNow = timeService.getFormattedCurrentGameTime();
        timeService.pauseGame();
        log.info("存檔並結束遊戲!!!!!!!!!");
        String message = "<遊戲結束> :  時間與車輛模擬已暫停。目前時間: " + timeDtoNow;
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/resetGame")//重設遊戲
    public ResponseEntity<TimeDto> resetGame() {
        TimeDto resetTimeDto = timeService.resetGame();
        resetService.resetGameData();
        return new ResponseEntity<>(resetTimeDto, HttpStatus.OK);
    }

    @GetMapping("/gameState")
    public ResponseEntity<String> gameState() {
        boolean state = timeService.isGamePaused();
        String message = state ? "目前時間車流暫停" : "目前時間車流進行中";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
