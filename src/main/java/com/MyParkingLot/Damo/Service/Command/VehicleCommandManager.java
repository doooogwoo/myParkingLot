package com.MyParkingLot.Damo.Service.Command;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
public class VehicleCommandManager {
    // 任務排隊執行順序（使用 Queue）
    private final Queue<VehicleCommand> queue = new LinkedList<>();

    // 執行過的歷史紀錄（文字記錄）
    @Getter
    private final List<String> history = new ArrayList<>();

    public void addCommand(VehicleCommand vehicleCommand){
        queue.offer(vehicleCommand);
    }

    public void runAll() {
        while (!queue.isEmpty()) {
            //多形概念
            // 拿出的是介面 VehicleCommand
            // 執行的方法是具體子類別的邏輯
            VehicleCommand vehicleCommand = queue.poll();//從先進去的開始拿
            vehicleCommand.execute();//執行
            history.add(vehicleCommand.getDescription());//紀錄
        }
    }
    public void runOne() {
        if (!queue.isEmpty()) {
            //多形概念
            // 拿出的是介面 VehicleCommand
            // 執行的方法是具體子類別的邏輯
            VehicleCommand vehicleCommand = queue.poll();//從先進去的開始拿
            vehicleCommand.execute();//執行
            history.add(vehicleCommand.getDescription());//紀錄
        }
    }

    public void printHistory(){
        System.out.println("執行紀錄:");
        for (String record : history){
            System.out.println("- " +record);
        }
    }

}
