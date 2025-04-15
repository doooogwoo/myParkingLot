package com.MyParkingLot.Damo.Service.orchestrator.parkingService;

import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import jakarta.transaction.Transactional;

public interface ParkingService {
    //void vehicleEntering();


    @Transactional
        //（指派 → 設定 → 驗證 → 儲存）
    void vehicleEntering(Vehicle vehicle);

    @Transactional
        //（找車 → 驗證 → 清空關聯 → 儲存 → 推播）
    void vehicleLeaving(Long vehicleId);
}
