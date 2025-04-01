package com.MyParkingLot.Damo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@Entity
public class TimeRecord {
    @Id
    private int timeId = 1; //--->只會有一個時間紀錄

    @Getter
    @Column(nullable = false,name = "record_history", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastGameTime;

    @Getter
    @Column(nullable = false)
    private long lastRealTimestamp;  // 現實時間戳記

    public TimeRecord() {
    }

    public TimeRecord(LocalDateTime lastGameTime, long lastRealTimestamp) {
        this.lastGameTime = lastGameTime;
        this.lastRealTimestamp = lastRealTimestamp;
    }

    public LocalDateTime getLastGameTime() {
        return lastGameTime;
    }

    public void setLastGameTime(LocalDateTime lastGameTime) {
        this.lastGameTime = lastGameTime;
    }

    public long getLastRealTimestamp() {
        return lastRealTimestamp;
    }

    public void setLastRealTimestamp(long lastRealTimestamp) {
        this.lastRealTimestamp = lastRealTimestamp;
    }
}
