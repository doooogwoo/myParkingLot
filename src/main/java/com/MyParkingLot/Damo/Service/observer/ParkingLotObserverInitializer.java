package com.MyParkingLot.Damo.Service.observer;

import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

//在系統一開始的時候就先準備好把停車場都註冊成觀察者
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class ParkingLotObserverInitializer {
//    private final ParkingLotIncome parkingLotIncome;
//    private final ParkingLotRepository parkingLotRepository;
//    @PostConstruct
//    private void observerLotInitializer(){
//        List<ParkingLot> lots = parkingLotRepository.findAll();
//        lots.forEach(parkingLotIncome::registerObserver);
//        log.info("停車場觀察者註冊完成");
//    }
//}
