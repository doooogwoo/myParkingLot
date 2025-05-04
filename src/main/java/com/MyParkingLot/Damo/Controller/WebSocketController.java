package com.MyParkingLot.Damo.Controller;

import com.MyParkingLot.Damo.Service.websocket.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/webSocket")
@RequiredArgsConstructor
public class WebSocketController {
    private final WebSocketService webSocketService;
    @PostMapping("/autoStart/{lotId}" )
    public ResponseEntity<String> startParkingSpaceStatus(@PathVariable Long lotId){
     webSocketService.startSpaceInfoPush(lotId);
     String message = "停車場狀態已開始進行推播";
     return ResponseEntity.ok(message);
    }

    @PostMapping("/autoStop/{lotId}" )
    public ResponseEntity<String> stopParkingSpaceStatus(@PathVariable Long lotId){
        webSocketService.stopSpaceInfoPush(lotId);
        String message = "停車場狀態已停止推播";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/spaceStatus")
    public ResponseEntity<List<String>> getAllPushed(){
        List<String> status = webSocketService.getActiveLots();
        return ResponseEntity.ok(status);
    }
}
