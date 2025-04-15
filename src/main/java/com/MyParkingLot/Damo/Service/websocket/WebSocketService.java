package com.MyParkingLot.Damo.Service.websocket;

import com.MyParkingLot.Damo.Payload.dto.ParkingSpaceDto;
import com.MyParkingLot.Damo.Payload.dto.ParkingSpaceViewDto;
import com.MyParkingLot.Damo.Service.logic.ParkingSpaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final ParkingSpaceService parkingSpaceService;
    private final ObjectMapper objectMapper;

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
}
