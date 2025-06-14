package com.MyParkingLot.Damo.domain.Model;

import com.MyParkingLot.Damo.Exception.APIException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeeklyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weeklyReportId;
    private LocalDateTime weekStartDate;
    private String parkingLotName;

    //當週使用狀況統計
    private double usageRate;               // 使用率（總使用格數 / 總格數）
    private int totalVehiclesServed;        // 本週總服務車輛數
    private double averageTimePerVehicle;   // 平均停車時間（分鐘或小時）

    // 收入分析
    private int totalIncome;                // 本週總收入
    private double averageIncomePerVehicle; // 每輛車帶來的平均收入

    //累積變數
//    private int totalUsedTime;//總使用時數


    @ManyToOne
    @JoinColumn(name = "parkingLot_id")
    private ParkingLot parkingLot;


    //-----------------------------------------------------------
    // (之後優化）
    // private int managementFee;
    // private int netProfit;
    // private double disabilityUsageRate;
    // private double electricUsageRate;
}
