package com.MyParkingLot.Damo.Service.time;

import com.MyParkingLot.Damo.Payload.dto.TimeDto;


public interface TimeService {
//
//    // 存檔遊戲時間/及時更新遊戲進度
//    TimeDto saveGameTime(TimeDto timeDto);

    //查詢時間
    public String getFormattedCurrentGameTime() ;

    // 存檔遊戲時間/及時更新遊戲進度
    TimeDto saveGameTime();

    TimeDto startGame();


    TimeDto resetGame();
}
