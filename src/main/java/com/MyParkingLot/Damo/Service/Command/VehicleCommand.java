package com.MyParkingLot.Damo.Service.Command;

import com.MyParkingLot.Damo.domain.Model.Vehicle;

public interface VehicleCommand {
    void execute();

    String getDescription(); // 用來記錄歷史或顯示日誌
}
