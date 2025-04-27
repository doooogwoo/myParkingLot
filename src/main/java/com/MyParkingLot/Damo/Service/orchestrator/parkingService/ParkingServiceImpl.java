package com.MyParkingLot.Damo.Service.orchestrator.parkingService;

import com.MyParkingLot.Damo.Exception.APIException;
import com.MyParkingLot.Damo.Exception.BusinessException;
import com.MyParkingLot.Damo.Exception.ErrorCode;
import com.MyParkingLot.Damo.Exception.ResourceNotFoundException;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Repository.ParkingSpaceRepository;
import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.Service.FeeStrategy.FeeStrategy;
import com.MyParkingLot.Damo.Service.FeeStrategy.FeeStrategyFactory;
import com.MyParkingLot.Damo.Service.logic.ParkingTicketServiceImpl;
import com.MyParkingLot.Damo.Service.observer.ParkingLotIncome;
import com.MyParkingLot.Damo.Service.observer.VehicleEvent;
import com.MyParkingLot.Damo.Service.time.TimeManager;
import com.MyParkingLot.Damo.domain.Model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingServiceImpl implements ParkingService {
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final TimeManager timeManager;
    private final VehicleRepository vehicleRepository;
    private final ParkingTicketServiceImpl parkingTicketService;
    private final ParkingLotRepository parkingLotRepository;
    private final FeeStrategyFactory feeStrategyFactory;
    private final ParkingLotIncome parkingLotIncome;

    @Transactional
    @Override //（指派 → 設定 → 驗證 → 儲存）
    public void vehicleEntering(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        ParkingSpace space = assignSpace(vehicle);
        if (space == null) {
            log.info("🚫 停車場已滿，車輛 {} 放棄進場", vehicle.getLicense());

            vehicleRepository.delete(vehicle);//進場失敗，直接刪除資料
            return;
        }
        vehicleRepository.save(vehicle);
        space.assignVehicle(vehicle);
        parkingSpaceRepository.save(space);
        log.info("✅ 車輛 {} 成功進場，進入停車場 {}，停在車位 {} (樓層 {})",
                vehicle.getLicense(),
                space.getParkingLot().getParkingLotName(),
                space.getParkingSpaceId(),
                space.getFloor());
    }


    @Transactional
    @Override//（找車 → 驗證 → 清空關聯 → 儲存 → 推播）
    public void vehicleLeaving(Long vehicleId) {
        //先找車
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "vehicleId", vehicleId));
        //再找對應車輛的停車場
        ParkingSpace parkingSpace = vehicle.getParkingSpace();

        //得到費用--> int getSpaceIncome
        parkingSpace.setSpaceIncome(getSpaceIncome(vehicle));

        //驗證 & 清空關聯
        parkingSpace.unassignVehicle();

        vehicle.setActualLeaveTime(timeManager.getCurrentGameTime());

        //儲存
        vehicleRepository.save(vehicle);
        parkingSpaceRepository.save(parkingSpace);
        log.info("🚗 車輛 {} 已離場，停車費用為 {}，離開車位 {}",
                vehicle.getLicense(), parkingSpace.getSpaceIncome(), parkingSpace.getParkingSpaceId());
        //離場流程結束後產生停車事件，通知
        VehicleEvent event = new VehicleEvent(vehicle,parkingSpace.getSpaceIncome());
        //parkingLotIncome（Subject）--->通知所有已經註冊的觀察者（Observer)(像是parkingLot)
        //「嘿，有一個新的事件發生了！請你們各自看看要不要處理！」
        parkingLotIncome.notifyObservers(event);
        ParkingLot lot = parkingSpace.getParkingLot();
        parkingLotRepository.save(lot);
        log.info("停車場目前收入 {}",lot.getIncome());
    }

    //計算費用
    private int getSpaceIncome(Vehicle vehicle) {
        if (vehicle.getParkingDuration() == null) {
            throw new BusinessException(ErrorCode.VEHICLE_LEAVE_SPACE_NO_HOURS);
        }

        ParkingTicket ticket = vehicle.getParkingSpace().getParkingLot().getParkingTicket();

        FeeStrategy feeStrategy = feeStrategyFactory.create(ticket);
        int fee = feeStrategy.calculateFee(vehicle);
        vehicle.setFee(fee);
        return fee;
    }


    public  ParkingSpace  assignSpace(Vehicle vehicle){
        //選擇聽車廠停入
        List<ParkingLot> parkingLotList = parkingLotRepository.findAll();
        ParkingLot parkingLot = randomParkingLot(parkingLotList);
        vehicle.setParkingLot(parkingLot);
        //排查停車場是否有空位
        List<ParkingSpace> spaces = parkingLot.getParkingSpaceList();
        List<ParkingSpace> availableSpaces = spaces
                .stream().filter(space -> !space.isOccupied())
                .toList();
        if (availableSpaces.isEmpty()) {
            log.warn("⚠️ 偵測到停車場 {} 已滿, id為{}，跳過此車輛", parkingLot.getParkingLotName(), parkingLot.getParkingLotId());
            return null; //-->沒有車位，直接跳下一輪
        }

        Random random = new Random();
        ParkingSpace parkingSpace = availableSpaces.get(random.nextInt( availableSpaces.size()));
        //ParkingSpace parkingSpace = new ParkingSpace();
        vehicle.assignParkingSpace(parkingSpace);

        log.info("車輛 {} 被分配至停車場 {} 的車位 {} (type: {}) ",
                vehicle.getLicense(),
                parkingLot.getParkingLotName(),
                parkingSpace.getParkingSpaceId(),
                parkingSpace.getParkingSpaceType());
                //vehicle.getExpectedVehicleLeaveTime());

        return parkingSpace;
    }


    //隨機抽取停車場(考慮抽出擴展
    private ParkingLot randomParkingLot(List<ParkingLot> parkingLots) {
        Random random = new Random();
        if (parkingLots == null) throw new APIException("停車場未創建");
        int index = random.nextInt(parkingLots.size());
        return parkingLots.get(index);
    }
}
