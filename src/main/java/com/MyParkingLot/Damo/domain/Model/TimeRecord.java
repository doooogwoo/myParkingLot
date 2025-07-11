package com.MyParkingLot.Damo.domain.Model;

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
@NoArgsConstructor
@Entity
public class TimeRecord {
    @Id
    private int timeId = 1; //--->只會有一個時間紀錄


    @Column(nullable = false,name = "record_history", columnDefinition = "DATETIME")
    private LocalDateTime recordHistory;

    @Column(nullable = false)
    private long lastRealTimestamp;  // 現實時間戳記

//    public TimeRecord() {
//    }

    public TimeRecord(LocalDateTime recordHistory, long lastRealTimestamp) {
        this.recordHistory = recordHistory;
        this.lastRealTimestamp = lastRealTimestamp;
    }

}
