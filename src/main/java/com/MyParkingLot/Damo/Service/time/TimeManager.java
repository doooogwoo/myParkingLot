package com.MyParkingLot.Damo.Service.time;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class TimeManager {
    private static final int TIME_SCALE = 240; // 1 秒 = 4 分鐘
    private LocalDateTime gameStartTime;
    private long startNanoTime;

    public void initGameTime(LocalDateTime savedGameTime, long savedRealTimestamp) {
        this.gameStartTime = savedGameTime;
        this.startNanoTime = System.nanoTime() - ((System.currentTimeMillis() - savedRealTimestamp) * 1_000_000);
    }

    public LocalDateTime getCurrentGameTime() {
        System.out.println("startNanoTime" + startNanoTime);
        long elapsedRealSeconds = (System.nanoTime() - startNanoTime) / 1_000_000_000;
        long elapsedGameSeconds = elapsedRealSeconds * TIME_SCALE;
        System.out.println("getCurrentGameTime測試" + elapsedGameSeconds);
        return gameStartTime.plusSeconds(elapsedGameSeconds);
    }
}
