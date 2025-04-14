package com.MyParkingLot.Damo.Service.report;

import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.Service.factory.ReportFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportFactoryTest {
    private ReportFactory reportFactory;

    @BeforeEach
    void setUp() {
        // 因為這些方法不會用到 repository
        reportFactory = new ReportFactory(null, null, null);
    }

    @Test
    void testCalculateUsageRate() {
        // TODO: 建立 3 台車、容量為 10，預期使用率為 0.3
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setCapacity(10);

        Vehicle v1 = new Vehicle();
        //v1.setParkingDuration(Duration.ofHours(4));
        Vehicle v2 = new Vehicle();
        //v2.setParkingDuration(Duration.ofHours(5));
        Vehicle v3 = new Vehicle();
        //v3.setParkingDuration(Duration.ofHours(2));

        List<Vehicle> vehicles = List.of(v1,v2,v3);

        double usageRate = reportFactory.calculateUsageRate(vehicles, parkingLot.getCapacity());

        assertEquals(0.3, usageRate, 0.01);
    }

    @Test
    void testCalculateAverageTime() {
        // TODO: 建立三台車停 60、90、30 分鐘，預期平均時間為 60 分鐘
        Vehicle v1 = new Vehicle();
        v1.setParkingDuration(Duration.ofHours(1));
        Vehicle v2 = new Vehicle();
        v2.setParkingDuration(Duration.ofHours(2));
        Vehicle v3 = new Vehicle();
        v3.setParkingDuration(Duration.ofHours(3));

        List<Vehicle> vehicles = List.of(v1,v2,v3);
        double calculateAverageTime = reportFactory.calculateAverageTime(vehicles);

        assertEquals(2, calculateAverageTime);
    }

    @Test
    void testCalculateTotalIncome() {
        // TODO: 建立三台車，各有 fee 100、150、200，預期總收入為 450
        Vehicle v1 = new Vehicle();
        v1.setFee(100);
        Vehicle v2 = new Vehicle();
        v2.setFee(150);
        Vehicle v3 = new Vehicle();
        v3.setFee(200);

        List<Vehicle> vehicles = List.of(v1,v2,v3);
        int  calculateTotalIncome = reportFactory.calculateTotalIncome(vehicles);

        assertEquals(450, calculateTotalIncome);
    }

    @Test
    void testCalculateAverageIncomePerVehicle() {
        // TODO: 使用總收入 450、車輛數 3，預期平均收入為 150
        Vehicle v1 = new Vehicle();
        v1.setFee(100);
        Vehicle v2 = new Vehicle();
        v2.setFee(150);
        Vehicle v3 = new Vehicle();
        v3.setFee(200);

        List<Vehicle> vehicles = List.of(v1,v2,v3);
        int  calculateTotalIncome = reportFactory.calculateTotalIncome(vehicles);
        double calculateAverageIncomePerVehicle = reportFactory
                .calculateAverageIncomePerVehicle(vehicles,calculateTotalIncome);

        assertEquals(450/3, calculateAverageIncomePerVehicle);
    }
}
