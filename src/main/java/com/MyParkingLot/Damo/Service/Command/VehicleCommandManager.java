package com.MyParkingLot.Damo.Service.Command;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
@Component
public class VehicleCommandManager {

    private final Queue<VehicleCommand> enterQueue = new LinkedList<>();
    private final Queue<VehicleCommand> leaveQueue = new LinkedList<>();

    @Getter
    private final List<String> history = new ArrayList<>();

    public void addCommand(VehicleCommand vehicleCommand){
        if (vehicleCommand instanceof EnterVehicleCommand) {
            enterQueue.offer(vehicleCommand);
            log.info("加入進場指令佇列：{}", vehicleCommand.getDescription());
        } else if (vehicleCommand instanceof LeaveVehicleCommand) {
            leaveQueue.offer(vehicleCommand);
            log.info(" 加入離場指令佇列：{}", vehicleCommand.getDescription());
        } else {
            throw new IllegalArgumentException("不支援的指令類型：" + vehicleCommand.getClass());
        }
    }

    public void runAll() {
        log.info("🚦開始執行進場指令，數量：{}", enterQueue.size());
        while (!enterQueue.isEmpty()) {
            VehicleCommand cmd = enterQueue.poll();
            cmd.execute();
            history.add(cmd.getDescription());
        }

        log.info("🚦開始執行離場指令，數量：{}", leaveQueue.size());
        while (!leaveQueue.isEmpty()) {
            VehicleCommand cmd = leaveQueue.poll();
            cmd.execute();
            history.add(cmd.getDescription());
        }
    }

    public void runOne() {
        if (!leaveQueue.isEmpty()) {
            VehicleCommand cmd = leaveQueue.poll();
            cmd.execute();
            history.add(cmd.getDescription());
        } else if (!enterQueue.isEmpty()) {
            VehicleCommand cmd = enterQueue.poll();
            cmd.execute();
            history.add(cmd.getDescription());
        }
    }

    public void printHistory(){
        System.out.println("執行紀錄:");
        for (String record : history){
            System.out.println(" - " + record);
        }
    }

    public int getEnterQueueSize() {
        return enterQueue.size();
    }

    public int getLeaveQueueSize() {
        return leaveQueue.size();
    }
}
