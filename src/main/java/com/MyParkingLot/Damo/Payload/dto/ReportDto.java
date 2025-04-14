package com.MyParkingLot.Damo.Payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private Long parkingLotId;
    private String parkingLotName;
    private LocalDateTime weekStartDate;

    // 使用狀況統計
    private double usageRate;               // 使用率（總使用格數 / 總格數）
    private int totalVehiclesServed;        // 本週總服務車輛數
    private double averageTimePerVehicle;   // 平均停車時間（分鐘或小時）

    // 收入分析
    private int totalIncome;                // 本週總收入
    private double averageIncomePerVehicle; // 每輛車帶來的平均收入

    // (之後優化）
    // private int managementFee;
    // private int netProfit;
    // private double disabilityUsageRate;
    // private double electricUsageRate;
}
