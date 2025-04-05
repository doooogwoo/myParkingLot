package com.MyParkingLot.Damo.Service.Command;

public interface VehicleCommand {
    void execute();
    String getDescription(); // 用來記錄歷史或顯示日誌
}
