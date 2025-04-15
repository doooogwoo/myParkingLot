package com.MyParkingLot.Damo.Service.GameScheduler;

import com.MyParkingLot.Damo.Service.Command.VehicleCommandManager;
import com.MyParkingLot.Damo.Service.factory.ParkingServiceFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class GameScheduler {

    private final ParkingServiceFactory parkingServiceFactory;
    private final VehicleCommandManager vehicleCommandManager;

    // 預備加入時間模擬功能
//    public void tick() {
//        parkingServiceFactory.autoAssignVehicle();
//        parkingServiceFactory.autoLeaveVehicles();
//
//        vehicleCommandManager.runAll();
//    }
    public void tick() {
        log.info("🎯 進場佇列 / 離場佇列 狀態");

        // ① 建立車輛 ➜ 產生進場指令（尚未 assign）
        parkingServiceFactory.autoAssignVehicle();

        // ② 執行所有進場/離場指令（此時車輛才被 assign，實際存入 DB）
        vehicleCommandManager.runAll();
        log.info("執行所有進場/離場指令（此時車輛才被 assign，實際存入 DB）");

        // ③ 再次檢查 DB ➜ 哪些車輛時間到 ➜ 發出離場指令（這些車輛才真的 assign 過）
        parkingServiceFactory.autoLeaveVehicles();
        vehicleCommandManager.runAll();
    }


    @Scheduled(fixedRate = 5000)
    public void scheduledTick() {
        tick();
    }
}
