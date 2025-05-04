package com.MyParkingLot.Damo.Service.logic;

import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CityLocationImpl implements LocationService{
    private ParkingLotRepository lotRepository;
    private Map<String,int[]> map = new HashMap<>();

    @PostConstruct
    private void initMap() {
        map.put("L1", new int[]{100, 100});
        map.put("L2", new int[]{250, 100});
        map.put("L3", new int[]{500, 100});
        map.put("L4", new int[]{750, 100});
        map.put("L5", new int[]{900, 100});
        map.put("L6", new int[]{100, 250});
        map.put("L7", new int[]{250, 250});
        map.put("L8", new int[]{650, 250});
        map.put("L9", new int[]{900, 250});
        map.put("L10", new int[]{100, 400});
        map.put("L11", new int[]{300, 400});
        map.put("L12", new int[]{600, 400});
        map.put("L13", new int[]{800, 400});
        map.put("L14", new int[]{100, 550});
        map.put("L15", new int[]{300, 550});
        map.put("L16", new int[]{700, 550});
        map.put("L17", new int[]{900, 550});
        map.put("L18", new int[]{200, 700});
        map.put("L19", new int[]{500, 700});
        map.put("L20", new int[]{800, 700});
        map.put("L21", new int[]{100, 850});
        map.put("L22", new int[]{350, 850});
        map.put("L23", new int[]{600, 850});
        map.put("L24", new int[]{850, 850});
        map.put("L25", new int[]{500, 980});
    }


}
