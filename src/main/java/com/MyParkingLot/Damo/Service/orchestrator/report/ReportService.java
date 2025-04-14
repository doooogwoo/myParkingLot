package com.MyParkingLot.Damo.Service.orchestrator.parkingService.report;

import com.MyParkingLot.Damo.Payload.dto.ReportDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    List<ReportDto> getAllReport();

    //產生報表
    ReportDto buildReport(Long parkingLotId, LocalDateTime gameNow);
}
