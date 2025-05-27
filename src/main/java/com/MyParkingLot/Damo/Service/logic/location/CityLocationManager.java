package com.MyParkingLot.Damo.Service.logic.location;

import com.MyParkingLot.Damo.Exception.APIException;
import com.MyParkingLot.Damo.Payload.dto.location.LocationInfo;
import com.MyParkingLot.Damo.Payload.dto.location.LocationMapDto;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CityLocationManager {
    private final ModelMapper mapper;
    private final ParkingLotRepository parkingLotRepository;
    private Map<String, LocationInfo> map = new HashMap<>();

    @PostConstruct
    private void initMap() {
        map.put("L1", new LocationInfo(100, 100, false));
        map.put("L2", new LocationInfo(350, 100, false));
        map.put("L3", new LocationInfo(500, 150, false));
        map.put("L4", new LocationInfo(100, 250, false));
        map.put("L5", new LocationInfo(250, 250, false));
        map.put("L6", new LocationInfo(650, 250, false));
        map.put("L7", new LocationInfo(100, 450, false));
        map.put("L8", new LocationInfo(300, 400, false));
        map.put("L9", new LocationInfo(100, 550, false));
        map.put("L10", new LocationInfo(700, 550, false));
        map.put("L11", new LocationInfo(300, 700, false));
        map.put("L12", new LocationInfo(450, 700, false));
        map.put("L13", new LocationInfo(500, 500, false));

        syncWithExistingLots();
    }

    private void syncWithExistingLots() {
        List<ParkingLot> existingLots = parkingLotRepository.findAll();
        for (ParkingLot lot : existingLots) {
            String locationId = lot.getLocationId(); // 注意這欄位名稱必須是你 DB 中的地圖位置 ID
            if (map.containsKey(locationId)) {
                map.get(locationId).setUsed(true);
            }
        }
    }

    public LocationInfo getLocationInfo(String id){
        LocationInfo info = map.get(id);
        return info;
    }

    // 檢查是否已被使用
    public boolean isUsed(String id) {
        LocationInfo info = map.get(id);
        return info != null && info.isUsed();
    }

    public void setUsed(String id,boolean used){
        LocationInfo info = map.get(id);
        if (info != null){
            info.setUsed(used);
        }
    }

    //隨機定地點
    public Map.Entry<String, LocationInfo> getRandomAvailableLocation() {
        List<Map.Entry<String, LocationInfo>> candidates = map.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("L1"))
                .filter(entry -> !entry.getValue().isUsed())
                .collect(Collectors.toList());

        Collections.shuffle(candidates); // 打亂順序
        return candidates.stream()
                .findFirst()
                .orElseThrow(() -> new APIException("地圖已滿（不含 L1），無可用位置"));
    }


    //之後優化的時候可以用(拆除停車場)
    public void releaseLocation(String id) {
        LocationInfo info = map.get(id);
        if (info != null) {
            info.setUsed(false); // 將該點標記為未使用
        }
    }

    //讓紀錄初始化的時候用
    public void resetAllLocations() {
        map.values().forEach(info -> info.setUsed(false));
    }


    public List<LocationMapDto> getAllLocations() {
        return map.entrySet().stream()
                .map(entry -> {
                    LocationMapDto dto = mapper.map(entry.getValue(), LocationMapDto.class);
                    dto.setLotId(entry.getKey());

                    parkingLotRepository.findByLocationId(entry.getKey())
                            .ifPresentOrElse(
                                    lot -> dto.setParkingLotName(lot.getParkingLotName()),
                                    () -> dto.setParkingLotName("尚未解鎖停車場")
                            );

                    return dto;
                })
                .collect(Collectors.toList());
    }


}
