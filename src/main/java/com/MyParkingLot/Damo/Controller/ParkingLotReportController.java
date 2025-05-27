package com.MyParkingLot.Damo.Controller;

import com.MyParkingLot.Damo.Payload.dto.ReportDto;
import com.MyParkingLot.Damo.Service.orchestrator.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
///api/report → 查詢所有報表
//
///api/report/generate/{id} → 產生指定停車場的報表
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ParkingLotReportController {
    private final ReportService reportService;

    @GetMapping("/getAllReports")//顯示全部停車場，還有基本報表
    public ResponseEntity<List<ReportDto>> getAllParkingLotReport(){
        List<ReportDto> reports = reportService.getAllReport();
        return new ResponseEntity<List<ReportDto>>(reports, HttpStatus.OK);
    }

    @PostMapping("/report/generateReport/{id}")
    public ResponseEntity<ReportDto> generateReport(@PathVariable Long id){
        LocalDateTime now = LocalDateTime.now();
        ReportDto reportdto = reportService.buildReport(id,now);
        return ResponseEntity.ok(reportdto);
    }
}
