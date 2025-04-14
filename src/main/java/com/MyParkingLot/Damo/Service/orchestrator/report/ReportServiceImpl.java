package com.MyParkingLot.Damo.Service.orchestrator.parkingService.report;

import com.MyParkingLot.Damo.domain.Model.WeeklyReport;
import com.MyParkingLot.Damo.Payload.dto.ReportDto;
import com.MyParkingLot.Damo.Repository.WeeklyReportRepository;
import com.MyParkingLot.Damo.Service.factory.ReportFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportFactory reportFactory;
    private final ModelMapper mapper;
    private final WeeklyReportRepository weeklyReportRepository;

    @Override
    //產生報表
    public ReportDto buildReport(Long parkingLotId, LocalDateTime gameNow){
        WeeklyReport weeklyReport = reportFactory.generateWeeklyReport(parkingLotId, gameNow);
        return mapper.map(weeklyReport,ReportDto.class);
    }
    @Override
    //讀取報表
    public List<ReportDto> getAllReport() {
        List<WeeklyReport> weeklyReport = weeklyReportRepository.findAll();
        List<ReportDto> reportDtos = weeklyReport.stream()
                .map(report -> mapper.map(report,ReportDto.class)).toList();
        return reportDtos;
    }

}
