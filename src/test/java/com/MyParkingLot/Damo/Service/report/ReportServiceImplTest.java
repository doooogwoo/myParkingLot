package com.MyParkingLot.Damo.Service.report;

import com.MyParkingLot.Damo.Service.orchestrator.parkingService.report.ReportServiceImpl;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.WeeklyReport;
import com.MyParkingLot.Damo.Payload.dto.ReportDto;
import com.MyParkingLot.Damo.Repository.WeeklyReportRepository;
import com.MyParkingLot.Damo.Service.factory.ReportFactory;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceImplTest {

    @Test
    void testGetAllReport_shouldReturnMappedDtoList() {
        // Arrange
        WeeklyReportRepository weeklyReportRepository = mock(WeeklyReportRepository.class);
        ModelMapper mapper = new ModelMapper();
        ReportFactory factory = mock(ReportFactory.class); // 不在這個測試中使用

        ReportServiceImpl reportService = new ReportServiceImpl(factory, mapper, weeklyReportRepository);

        ParkingLot lot = new ParkingLot();
        lot.setParkingLotId(1L);

        WeeklyReport report = WeeklyReport.builder()
                .parkingLot(lot)
                .weekStartDate(LocalDateTime.of(2025, 4, 1, 0, 0))
                .usageRate(0.75)
                .totalVehiclesServed(12)
                .averageTimePerVehicle(60)
                .totalIncome(1800)
                .averageIncomePerVehicle(150)
                .build();

        when(weeklyReportRepository.findAll()).thenReturn(List.of(report));

        // Act
        List<ReportDto> result = reportService.getAllReport();
        System.out.println(result);

        // Assert
        assertEquals(1, result.size());
        ReportDto dto = result.get(0);
        assertEquals(1L, dto.getParkingLotId());
        assertEquals(0.75, dto.getUsageRate());
        assertEquals(1800, dto.getTotalIncome());
        assertEquals(150, dto.getAverageIncomePerVehicle());
    }
}
