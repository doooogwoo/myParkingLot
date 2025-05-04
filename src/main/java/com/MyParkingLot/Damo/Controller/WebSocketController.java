package com.MyParkingLot.Damo.Controller;

import com.MyParkingLot.Damo.Service.websocket.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webSocket")
@RequiredArgsConstructor
public class WebSocketController {
    private final WebSocketService webSocketService;
    @GetMapping("/lotSpaceStatus/{lotId}" )
    public ResponseEntity<String> getParkingSpaceStatus(@PathVariable Long lotId){
     webSocketService.sendParkingLotSpaceUpdate(lotId);
     String message = "停車場狀態已推播";
     return ResponseEntity.ok(message);
    }
}
