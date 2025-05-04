package com.MyParkingLot.Damo.Service.websocket;

import com.MyParkingLot.Damo.Exception.ResourceNotFoundException;
import com.MyParkingLot.Damo.Payload.dto.ParkingSpaceDto;
import com.MyParkingLot.Damo.Payload.dto.ParkingSpaceViewDto;
import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Service.logic.ParkingSpaceService;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final ParkingSpaceService parkingSpaceService;
    private final ObjectMapper objectMapper;
    private final ParkingLotRepository lotRepository;
    private final Set< Long> spaceStatus = ConcurrentHashMap.newKeySet();
    public void sendParkingLotSpaceUpdate(Long parkingLotId) {
        List<ParkingSpaceViewDto> spaces = parkingSpaceService.getSpaceStatusList(parkingLotId);
        String topic = "/topic/parkinglot/" + parkingLotId + "/spaces";
        log.info("🔔 WebSocket 正在推播車位更新給 {}", topic);

        try {
            String json = objectMapper.writeValueAsString(spaces);
            log.info("🚦 推播資料內容 JSON：{}", json);
        } catch (Exception e) {
            log.error("❌ JSON 轉換失敗", e);
        }

        messagingTemplate.convertAndSend(topic, spaces);
    }

    //turn on
    public  void startSpaceInfoPush(Long id){
        spaceStatus.add(id);
        log.info("啟動自動推撥");
    }

    //turn off
    public  void stopSpaceInfoPush(Long id){
        spaceStatus.remove(id);
        log.info("關閉自動推撥");
    }

    //開啟後才會觸發自動推撥
    @Scheduled(fixedRate = 5000)
    public void scheduledTick() {
        if (spaceStatus.isEmpty()) return;
        log.info("定時推播執行中...");
        for (Long id:spaceStatus){
            sendParkingLotSpaceUpdate(id);
        }
    }

    public List<String> getActiveLots(){
        List<String> lotNames = spaceStatus.stream().map(
                LotId -> lotRepository.findById(LotId)
                        .map(ParkingLot::getParkingLotName)
                        .orElseThrow(() -> new ResourceNotFoundException("停車場找不到","停車場id",LotId))
                ).toList();
        return lotNames;
    }
}
