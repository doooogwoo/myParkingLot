package com.MyParkingLot.Damo.Controller;

import com.MyParkingLot.Damo.Payload.dto.TimeDto;
import com.MyParkingLot.Damo.Service.time.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("time")
public class TimeController {
    @Autowired
    private TimeService timeService;
    @GetMapping("/getCurrentTime") //--->查詢當前時間
    public ResponseEntity<String> getCurrentTime(){
        String timeDtoNow = timeService.getFormattedCurrentGameTime();
        return new ResponseEntity<String>(timeDtoNow,HttpStatus.OK);
    }

    @PostMapping("/saveTimeRecord") //-->存檔
    public ResponseEntity<TimeDto> saveTime(@RequestBody TimeDto timeDto){
        TimeDto time = timeService.saveGameTime(timeDto);
        return new ResponseEntity<TimeDto>(time,HttpStatus.OK);
    }

    @PostMapping("startGame") //開始遊戲
    public ResponseEntity<TimeDto> startGame(){
        TimeDto startTimeDto = timeService.startGame();
        return new ResponseEntity<TimeDto>(startTimeDto,HttpStatus.OK);
    }

    @PostMapping("resetGame")
    public ResponseEntity<TimeDto> resetGame(){
        TimeDto resetTimeDto = timeService.resetGame();
        return new ResponseEntity<>(resetTimeDto,HttpStatus.OK);
    }
}
