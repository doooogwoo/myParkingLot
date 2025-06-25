package com.MyParkingLot.Damo.Service.time;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TimeManager {
    private static final int TIME_SCALE = 3600;
    //240; // 1 秒 = 4 分鐘
    //1 秒現實時間 = 1 小時遊戲時間
    @Getter
    private LocalDateTime gameStartTime;
    private static final LocalDateTime DEFAULT_GAME_TIME = LocalDateTime.of(2030, 4, 5, 0, 0);

    //System.nanoTime()->回傳是long(奈秒)
    @Getter
    private long accumulatedRealSeconds = 0; // 儲存目前為止總共經過的現實秒數
    private long resumeNanoTime = 0;          // 最近一次 resume 的 nanoTime
    @Getter
    private boolean isPaused = true;          // 遊戲啟動時預設為暫停狀態

    //讀檔，還原上一次遊戲時間
    public void initGameTime(LocalDateTime savedGameTime, long savedRealTimestamp) {
        if (savedGameTime == null || savedRealTimestamp < 0) {
          initDefaultTime();
        } else {
            this.gameStartTime = savedGameTime;
            this.accumulatedRealSeconds = savedRealTimestamp;
            this.isPaused = true;
        }
        log.info("遊戲時間初始化完成 - 開始時間: {}, 累積現實秒數: {}",
                gameStartTime, accumulatedRealSeconds);
    }

    //初始化時間預設值
    @PostConstruct
    public void initDefaultTime() {
        if (this.gameStartTime == null) {
            this.gameStartTime = DEFAULT_GAME_TIME;
            this.accumulatedRealSeconds = 0;
            this.isPaused = true;
        }
        log.info("時間初始化完成: {}", DEFAULT_GAME_TIME);
    }

    //時間流動開始
    public void resumeTime() {
        if (isPaused) {
            resumeNanoTime = System.nanoTime();//紀錄幾毫秒(設立基準點)
            isPaused = false;
            log.info("時間已恢復流動");
        } else {
            log.debug("時間已在流動中，無需恢復");
        }
    }


    //把奈秒轉成秒
//    private long getElapsedSecondsSinceResume() {
//        return (System.nanoTime() - resumeNanoTime) / 1_000_000_000;
//    }
    private long getElapsedSecondsSinceResume() {
        return TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - resumeNanoTime);
    }

    //時間流動暫停
    public void pauseTime() {
        if (!isPaused) {
            accumulatedRealSeconds += getElapsedSecondsSinceResume();
            isPaused = true;
            log.info("時間已暫停，累積現實秒數: {} 秒", accumulatedRealSeconds);
        } else {
            log.debug("時間已暫停，無需再次暫停");
        }
    }

    //得到當前的遊戲時間
    public LocalDateTime getCurrentGameTime() {
        long totalElapsedRealSeconds = accumulatedRealSeconds;

        if (!isPaused) {
            totalElapsedRealSeconds += getElapsedSecondsSinceResume();
        }

        //時間縮放核心概念
        long elapsedGameSeconds = totalElapsedRealSeconds * TIME_SCALE;

        LocalDateTime result = gameStartTime.plusSeconds(elapsedGameSeconds);
        log.info("目前遊戲時間為: {}（已經過遊戲時間 {}）", result, formatGameDuration(elapsedGameSeconds));
        return result;
    }

    public String formatGameDuration(long gameSeconds) {
        long days = gameSeconds / (24 * 3600);
        long hours = (gameSeconds % (24 * 3600)) / 3600;
        long minutes = (gameSeconds % 3600) / 60;
        long seconds = gameSeconds % 60;

        return String.format("目前遊戲時間 %d 天 %d 小時 %d 分鐘 %d 秒", days, hours, minutes, seconds);
    }
}


//    public LocalDateTime getCurrentGameTime() {
//        log.info("startNanoTime: {}", startNanoTime);
//        long elapsedRealSeconds = (System.nanoTime() - startNanoTime) / 1_000_000_000;
//        long elapsedGameSeconds = elapsedRealSeconds * TIME_SCALE;
//        log.info("getCurrentGameTime 測試: {} (換算: {})",
//                gameStartTime.plusSeconds(elapsedGameSeconds), formatGameDuration(elapsedGameSeconds));
//        return gameStartTime.plusSeconds(elapsedGameSeconds);
//    }
