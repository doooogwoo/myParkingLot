package com.MyParkingLot.Damo.Service.observer;

import com.MyParkingLot.Damo.Service.Command.LeaveVehicleCommand;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
//@Component
//public class ParkingLotIncome implements ParkingCenter {
//    private List<ParkingObserver> observers = new ArrayList<>();
//    @Override
//    public void registerObserver(ParkingObserver parkingLotObserver) {
//        observers.add(parkingLotObserver);
//    }
//
//    @Override
//    public void removeObserver(ParkingObserver parkingLotObserver) {
//        observers.remove(parkingLotObserver);
//    }
//
//    @Override
//    public void notifyObservers(VehicleEvent event) {
//        for (ParkingObserver observer : observers) {
//            if (observer instanceof ParkingLot lot){
//                System.out.println("🔍 比對中：觀察者 lot ID = " + lot.getParkingLotId());
//                if (lot.getParkingLotId().equals(
//                        event.getVehicle().getParkingLot().getParkingLotId()
//                )) {
//                    System.out.println("✅ 匹配成功，執行 update()");
//                    lot.update(event);
//                }
//            }
//            //observer.update(event);
//        }
//        System.out.println("👀 目前觀察者清單數量：" + observers.size());
//
//    }
//}
