package com.MyParkingLot.Damo.Service.time;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Slf4j
public class TimeManager {
    private static final int TIME_SCALE = 3600;
            //240; // 1 秒 = 4 分鐘
    private LocalDateTime gameStartTime;
    private final LocalDateTime INIT_GAME_TIME = LocalDateTime.of(2030, 4, 5, 0, 0);
    private long startNanoTime;

    public void initGameTime(LocalDateTime savedGameTime, long savedRealTimestamp) {
        this.gameStartTime = savedGameTime;
        this.startNanoTime = System.nanoTime() - ((System.currentTimeMillis() - savedRealTimestamp) * 1_000_000);
    }

    @PostConstruct
    public void initDefaultTimeIfNeeded() {
        if (this.gameStartTime == null) {
            initGameTime(INIT_GAME_TIME, System.currentTimeMillis());
        }
        log.info("時間初始化完成: {}",INIT_GAME_TIME);
    }


    public LocalDateTime getCurrentGameTime() {
        log.info("startNanoTime: {}",startNanoTime);
        long elapsedRealSeconds = (System.nanoTime() - startNanoTime) / 1_000_000_000;
        long elapsedGameSeconds = elapsedRealSeconds * TIME_SCALE;
        log.info("getCurrentGameTime 測試: {} (換算: {})",
                gameStartTime.plusSeconds(elapsedGameSeconds), formatGameDuration(elapsedGameSeconds));
        return gameStartTime.plusSeconds(elapsedGameSeconds);
    }
    public String formatGameDuration(long gameSeconds) {
        long days = gameSeconds / (24 * 3600);
        long hours = (gameSeconds % (24 * 3600)) / 3600;
        long minutes = (gameSeconds % 3600) / 60;
        long seconds = gameSeconds % 60;

        return String.format("%d 天 %d 小時 %d 分鐘 %d 秒", days, hours, minutes, seconds);
    }

}
