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
    private final VehicleGameScheduler vehicleGameScheduler;
    private final ResetService resetService;
    @GetMapping("/getCurrentTime") //--->查詢當前時間
    public ResponseEntity<String> getCurrentTime(){
        String timeDtoNow = timeService.getFormattedCurrentGameTime();
        return new ResponseEntity<>(timeDtoNow,HttpStatus.OK);
    }

    @PostMapping("/saveTimeRecord") //-->存檔
    public ResponseEntity<TimeDto> saveTime(){
        TimeDto time = timeService.saveGameTime();
        return new ResponseEntity<>(time,HttpStatus.OK);
    }


    @PostMapping("/startGame") //---->開始遊戲
    public ResponseEntity<TimeDto> startGame(){
        TimeDto startTimeDto = timeService.startGame();
        vehicleGameScheduler.setRunning(true);
        return new ResponseEntity<>(startTimeDto,HttpStatus.OK);
    }

    @PostMapping("/saveAndExit") //-->存檔並結束遊戲
    public ResponseEntity<TimeDto> saveAndExit(){
        TimeDto time = timeService.saveGameTime();
        vehicleGameScheduler.setRunning(false);
        log.info("存檔並結束遊戲!!!!!!!!!");
        return new ResponseEntity<>(time,HttpStatus.OK);
    }

    @PostMapping("/resetGame")//重設遊戲
    public ResponseEntity<TimeDto> resetGame(){
        TimeDto resetTimeDto = timeService.resetGame();
        resetService.resetGameData();
        return new ResponseEntity<>(resetTimeDto,HttpStatus.OK);
    }
}
