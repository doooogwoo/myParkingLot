package com.MyParkingLot.Damo.Service.report;

import com.MyParkingLot.Damo.Service.orchestrator.parkingService.report.ReportServiceImpl;
import com.MyParkingLot.Damo.domain.Model.WeeklyReport;
import com.MyParkingLot.Damo.Payload.dto.ReportDto;
import com.MyParkingLot.Damo.Repository.WeeklyReportRepository;
import com.MyParkingLot.Damo.Service.factory.ReportFactory;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportServiceImpl_BuildReportTe {
    @Test
    void testBuildReport_shouldReturnMappedDto() {
        // Arrange
        WeeklyReportRepository weeklyReportRepository = mock(WeeklyReportRepository.class);
        ModelMapper mapper = new ModelMapper();

        // mock ReportFactory
        ReportFactory factory = mock(ReportFactory.class);

        WeeklyReport mockReport = WeeklyReport.builder()
                .totalIncome(1200)
                .usageRate(0.6)
                .averageIncomePerVehicle(200)
                .totalVehiclesServed(6)
                .averageTimePerVehicle(90)
                .weekStartDate(LocalDateTime.of(2025, 4, 7, 0, 0))
                .build();

        when(factory.generateWeeklyReport(eq(1L), any(LocalDateTime.class)))
                .thenReturn(mockReport);

        ReportServiceImpl reportService = new ReportServiceImpl(factory, mapper, weeklyReportRepository);

        // Act
        ReportDto dto = reportService.buildReport(1L, LocalDateTime.of(2025, 4, 10, 12, 0));

        // Assert
        assertNotNull(dto);
        assertEquals(1200, dto.getTotalIncome());
        assertEquals(0.6, dto.getUsageRate());
        assertEquals(200, dto.getAverageIncomePerVehicle());
        assertEquals(6, dto.getTotalVehiclesServed());
    }
}
