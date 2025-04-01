package com.MyParkingLot.Damo.Service.vehicle;

import com.MyParkingLot.Damo.Payload.VehicleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VehicleServiceTest {
    @Autowired
    private VehicleService vehicleService;

    @Test
    void testPrintGeneratedVehicle() {
        VehicleDto vehicle = vehicleService.addVehicle();
        System.out.println("🚗 生成的車輛物件如下：");
        System.out.println(vehicle);
        System.out.println(vehicleService.addVehicle());
    }
}

