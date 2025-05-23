package com.MyParkingLot.Damo.Payload.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeDto {
    private final int TIME_ID = 1; //--->只會有一個時間紀錄


    @Column(nullable = false)
    private String recordHistory;;

    @Getter
    @Column(nullable = false)
    private long lastRealTimestamp;  // 現實時間戳記

    public void setLastGameTime(String recordHistory) {
        this.recordHistory = recordHistory;
    }

    public long getLastRealTimestamp() {
        return lastRealTimestamp;
    }

    public void setLastRealTimestamp(long lastRealTimestamp) {
        this.lastRealTimestamp = lastRealTimestamp;
    }
}