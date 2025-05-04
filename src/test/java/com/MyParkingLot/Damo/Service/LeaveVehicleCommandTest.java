package com.MyParkingLot.Damo.Service;

import com.MyParkingLot.Damo.Service.Command.LeaveVehicleCommand;
//import com.MyParkingLot.Damo.Service.observer.ParkingCenter;
//import com.MyParkingLot.Damo.Service.observer.ParkingLotIncome;
//import com.MyParkingLot.Damo.Service.observer.VehicleEvent;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

//class LeaveVehicleCommandTest {
//
//    @Test
//    void testVehicleLeave_ShouldAddIncomeToParkingLot() {
//        // Arrange: 建立一個停車場 + 一台車
//        ParkingLot parkingLot = ParkingLot.builder()
//                .parkingLotName("TestLot")
//                .createAt(LocalDateTime.now())
//                .capacity(10)
//                .floors(1)
//                .build();
//
//        Vehicle vehicle = new Vehicle();
//        vehicle.setLicense("ABC-1234");
//        vehicle.setParkingLot(parkingLot);
//
//        int expectedFee = 100;
//
//        // 建立 ParkingCenter
//        ParkingCenter parkingCenter = new ParkingLotIncome();
//        parkingCenter.registerObserver(parkingLot); // ✅ 將 ParkingLot 註冊成觀察者
//
//        // Create 離場指令，這裡模擬簡化版
//        LeaveVehicleCommand command = new LeaveVehicleCommand(
//                vehicle,
//                null, // parkingService 可mock
//                null // websocketService 可mock
//        ) {
//            @Override
//            public void execute() {
//                // 模擬離場流程，直接發出事件
//                VehicleEvent event = new VehicleEvent();
//                event.setVehicle(vehicle);
//                event.setIncome(expectedFee);
//                parkingCenter.notifyObservers(event);
//            }
//        };
//
//        // Act: 執行指令
//        command.execute();
//
//        // Assert: 確認收入增加
//        assertEquals(expectedFee, parkingLot.getIncome());
//    }
//}
