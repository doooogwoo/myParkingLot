package com.MyParkingLot.Damo.Service.websocket;

import com.MyParkingLot.Damo.Payload.dto.ParkingSpaceDto;
import com.MyParkingLot.Damo.Payload.dto.ParkingSpaceViewDto;
import com.MyParkingLot.Damo.Service.logic.ParkingSpaceService;
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

    public void sendParkingLotSpaceUpdate(Long parkingLotId) {
        log.info("🔔 WebSocket 正在推播車位更新給 /topic/parkingLot/{}", parkingLotId);
        List<ParkingSpaceViewDto> spaces = parkingSpaceService.getSpaceStatusList(parkingLotId);
        log.info("📢 推播資料內容為：{}", spaces);
        messagingTemplate.convertAndSend("/topic/parkinglot/" + parkingLotId + "/spaces", spaces);
    }
}
