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
import com.MyParkingLot.Damo.Service.factory.ParkingServiceFactory;
import com.MyParkingLot.Damo.Service.logic.ParkingTicketServiceImpl;
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
    private final ParkingServiceFactory parkingServiceFactory;

    @Transactional
    @Override //（指派 → 設定 → 驗證 → 儲存）
    public void vehicleEntering(Vehicle vehicle) {
        ParkingSpace parkingSpace = parkingServiceFactory.assignSpace(vehicle);

        parkingSpace.assignVehicle(vehicle);

        vehicleRepository.save(vehicle);
        parkingSpaceRepository.save(parkingSpace);
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



}
