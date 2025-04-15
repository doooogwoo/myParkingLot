package com.MyParkingLot.Damo.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class WebSocketTestController {

    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/push")
    public String pushTestMessage() {
        try {
            Map<String, Object> mockData = new HashMap<>();
            mockData.put("parkingSpaceId", 999L);
            mockData.put("licenseCode", "TEST-1234");
            mockData.put("occupied", true);
            mockData.put("symbol", "E");

            List<Map<String, Object>> payload = Collections.singletonList(mockData);

            messagingTemplate.convertAndSend("/topic/parkinglot/1/spaces", payload);
            System.out.println("🚀 手動推播成功");
            return "推播成功 ✅";

        } catch (Exception e) {
            e.printStackTrace(); // 印出例外
            return "推播失敗 ❌：" + e.getMessage();
        }
    }
}
