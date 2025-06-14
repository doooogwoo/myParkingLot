package com.MyParkingLot.Damo.Service.factory;

import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.domain.Model.WeeklyReport;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.Repository.WeeklyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
@Component
@RequiredArgsConstructor
public class ReportFactory {
    private final VehicleRepository vehicleRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final WeeklyReportRepository weeklyReportRepository;
    public WeeklyReport generateWeeklyReport(Long parkingLotId, LocalDateTime gameNow) {
        // 1. 取得該停車場資訊
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new RuntimeException("停車場不存在"));

        // 2. 計算當週週期起始點與結束點
        LocalDateTime weekStart = calculateWeekStart(parkingLot.getCreateAt(), gameNow);
        LocalDateTime weekEnd = weekStart.plusDays(7);
        // 3. 撈資料：該週離場車輛紀錄
        List<Vehicle> vehicles = vehicleRepository
                .findVehiclesByParkingLotAndActualLeaveTimeBetween(parkingLotId, weekStart, weekEnd);

        // 4. 統計分析
        int totalServed = vehicles.size();
        int totalCapacity = parkingLot.getCapacity();

        double usageRate = calculateUsageRate(vehicles, totalCapacity);
        double averageTime = calculateAverageTime(vehicles);
        int totalIncome = calculateTotalIncome(vehicles);
        double avgIncomePerVehicle = calculateAverageIncomePerVehicle(vehicles, totalIncome);


        // 5. 建立 WeeklyReport Entity
        WeeklyReport report = WeeklyReport.builder()
                .parkingLot(parkingLot)
                .parkingLotName(parkingLot.getParkingLotName())
                .weekStartDate(weekStart)
                .usageRate(usageRate)
                .totalVehiclesServed(totalServed)
                .averageTimePerVehicle(averageTime)
                .totalIncome(totalIncome)
                .averageIncomePerVehicle(avgIncomePerVehicle)
                .build();

        weeklyReportRepository.save(report);
        return report;
    }
    private LocalDateTime calculateWeekStart(LocalDateTime createdAt, LocalDateTime now) {
        long days = Duration.between(createdAt, now).toDays();
        long weekIndex = days / 7;
        return createdAt.plusWeeks(weekIndex);
    }

    //private
    public double calculateUsageRate(List<Vehicle> vehicles, int totalCapacity) {
        if (totalCapacity == 0) return 0;
        return (double) vehicles.size() / totalCapacity;
    }

    //private
    public double calculateAverageTime(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) return 0;

        double totalHours = 0;
        for (Vehicle v : vehicles) {
                Duration stay = v.getParkingDuration();
                totalHours += stay.toHours();
        }
        return totalHours / vehicles.size();
    }

    //private
    public int calculateTotalIncome(List<Vehicle> vehicles) {
        return vehicles.stream()
                .mapToInt(v -> v.getFee())
                // 假設你有記錄每輛車的實際收費
                .sum();
    }

    //private
    public double calculateAverageIncomePerVehicle(List<Vehicle> vehicles, int totalIncome) {
        if (vehicles.isEmpty()) return 0;
        return (double) totalIncome / vehicles.size();
    }
}
