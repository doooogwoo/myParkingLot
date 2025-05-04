package com.MyParkingLot.Damo.Service.factory;

import com.MyParkingLot.Damo.Exception.APIException;
import com.MyParkingLot.Damo.Exception.BusinessException;
import com.MyParkingLot.Damo.Exception.ErrorCode;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Repository.ParkingSpaceRepository;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.domain.Model.ParkingSpaceType;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.Service.Command.EnterVehicleCommand;
import com.MyParkingLot.Damo.Service.Command.LeaveVehicleCommand;
import com.MyParkingLot.Damo.Service.Command.VehicleCommandManager;
import com.MyParkingLot.Damo.Service.websocket.WebSocketService;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingService;
import com.MyParkingLot.Damo.Service.time.TimeManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParkingServiceFactory {
    private final ParkingService parkingService;
    private final VehicleFactory vehicleFactory;
    private final VehicleRepository vehicleRepository;
    private final TimeManager timeManager;
    private final VehicleCommandManager vehicleCommandManager;
    private final WebSocketService webSocketService;

    public void debugVehicleStatus(Vehicle vehicle) {
        String license = vehicle.getLicense();
        log.info("[偵錯] 車牌：{}", license);

        if (vehicle.getParkingLot() == null) {
            log.warn("🚨 [偵錯] {} 尚未指派停車場", license);
        } else {
            log.info("✅ 已指派停車場：{}", vehicle.getParkingLot().getParkingLotName());
        }

        if (vehicle.getParkingSpace() == null) {
            log.warn("🚨 [偵錯] {} 尚未指派停車位", license);
        } else {
            log.info("✅ 已指派車位：{}", vehicle.getParkingSpace().getParkingSpaceId());
        }

        if (vehicle.getVehicleEnterTime() == null) {
            log.warn("🚨 [偵錯] {} 尚未設定進場時間", license);
        } else {
            log.info("✅ 進場時間：{}", vehicle.getVehicleEnterTime());
        }

        if (vehicle.getParkingDuration() == null) {
            log.warn("🚨 [偵錯] {} 尚未設定停車時間", license);
        } else {
            log.info("✅ 預計停車：{} 分鐘", vehicle.getParkingDuration().toMinutes());
        }

        if (vehicle.getActualLeaveTime() != null) {
            log.info("🕒 已離場時間：{}", vehicle.getActualLeaveTime());
        }
    }


    //自動指派
    public void autoAssignVehicle() {

        Vehicle vehicle = vehicleFactory.generateVehicle();
        log.info("偵錯debug 1  {}", vehicle.getLicense());
        debugVehicleStatus(vehicle);
        vehicleCommandManager.addCommand
                (new EnterVehicleCommand(vehicle, parkingService,vehicleRepository));

        log.info("已將車輛 {} 加入進場指令佇列", vehicle.getLicense());
        log.info("偵錯debug 2  {}", vehicle.getLicense());
        debugVehicleStatus(vehicle);
    }

    //自動離場，從"所有車庫裡判斷"
    public void autoLeaveVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        LocalDateTime now = timeManager.getCurrentGameTime();

        // 🔍 新增：列出尚未指派車位的車輛
        List<Vehicle> unassignedVehicles = vehicles.stream()
                .filter(v -> v.getParkingSpace() == null)
                .toList();

        if (!unassignedVehicles.isEmpty()) {
            log.warn("🚧 [偵錯] 以下車輛尚未完成車位指派（共 {} 輛）：", unassignedVehicles.size());
            unassignedVehicles.forEach(v ->
                    log.warn("    ➤ 車牌：{}（ID: {}）", v.getLicense(), v.getVehicleId())
            );
            vehicleRepository.deleteAll(unassignedVehicles);
            log.info("🗑️ 已刪除未完成指派的車輛資料！");
        } else {
            log.info("✅ 全部車輛皆已完成指派，無需清理。");

        }


        for (Vehicle vehicle : vehicles) {
            if (vehicle.getParkingLot() == null || vehicle.getParkingSpace() == null) {
                log.warn("尚未完成進場，跳過離場流程：{}", vehicle.getLicense());
                continue;
            }

            LocalDateTime expected = vehicle.getVehicleEnterTime().plus(vehicle.getParkingDuration());
            if (expected != null &&
                    vehicle.getActualLeaveTime() == null) {

                if (now.isAfter(expected)) {
                    String parkingLotName = vehicle.getParkingLot().getParkingLotName();
                    Long spaceId = vehicle.getParkingSpace().getParkingSpaceId();

                    log.info("⏰ now={}, 車牌={}, 預估離場時間={}", now, vehicle.getLicense(), expected);
                    log.info("自動離場成功：車牌 {}，停車場 {}，車位 {}",
                            vehicle.getLicense(), parkingLotName, spaceId);

                    //以命令模式進行離場
                    vehicleCommandManager.addCommand(
                            new LeaveVehicleCommand(vehicle, parkingService));
                    log.debug("📍 DEBUG 車輛 {} 是否完成指派？Lot={}, Space={}",
                            vehicle.getLicense(),
                            vehicle.getParkingLot() != null,
                            vehicle.getParkingSpace() != null);


                }
            }

        }
    }


}
