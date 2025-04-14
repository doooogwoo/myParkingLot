package com.MyParkingLot.Damo.Service.logic;


import com.MyParkingLot.Damo.Exception.ResourceNotFoundException;
import com.MyParkingLot.Damo.Service.time.TimeManager;
import com.MyParkingLot.Damo.Service.time.TimeService;
import com.MyParkingLot.Damo.domain.Model.TimeRecord;
import com.MyParkingLot.Damo.Payload.dto.TimeDto;
import com.MyParkingLot.Damo.Repository.TimeRecordRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TimeServiceImpl implements TimeService {
    private static final Logger logger = LoggerFactory.getLogger(TimeServiceImpl.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final TimeManager timeManager;
    private final TimeRecordRepository timeRecordRepository;
    private final ModelMapper mapper;
    @Autowired
    public TimeServiceImpl(TimeManager timeManager,
                           TimeRecordRepository timeRecordRepository,
                           ModelMapper mapper) {

        this.timeManager = timeManager;
        this.timeRecordRepository = timeRecordRepository;
        this.mapper = mapper;
    }



    @Override
    // 取得當前時間的字串（傳給前端）
    public String getFormattedCurrentGameTime() {
        logger.info("當前時間 : {}", timeManager.getCurrentGameTime().format(formatter));
        return timeManager.getCurrentGameTime().format(formatter);
    }

    @Override
    // 存檔遊戲時間/及時更新遊戲進度
    public TimeDto saveGameTime(TimeDto timeDto) {
        TimeRecord timeRecord = timeRecordRepository.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("找不到id","",1));
        timeRecord.setLastGameTime(timeManager.getCurrentGameTime());
        timeRecord.setLastRealTimestamp(System.currentTimeMillis());
        timeRecordRepository.save(timeRecord);
        logger.info("存檔 的時間記錄: {}", timeRecord.getLastGameTime());
        return mapper.map(timeRecord,TimeDto.class);
    }

    @Override
    public TimeDto startGame() {
        TimeRecord timeRecord = timeRecordRepository.findById(1)
                .orElseGet(() -> new TimeRecord(LocalDateTime.of(2030,4,5,0,0,0),System.currentTimeMillis()));

        timeManager.initGameTime(timeRecord.getLastGameTime(),timeRecord.getLastRealTimestamp());
        logger.info("遊戲開始時間 : {}", timeRecord);
        return mapper.map(timeRecord,TimeDto.class);
    }

    @Override
    public TimeDto resetGame() {
        TimeRecord newRecord = new TimeRecord(LocalDateTime.of(2030, 4, 5, 0, 0, 0), System.currentTimeMillis());
        timeRecordRepository.save(newRecord);

        timeManager.initGameTime(newRecord.getLastGameTime(),newRecord.getLastRealTimestamp());
        logger.info("重製 後的時間記錄: {}", newRecord.getLastGameTime());
        return mapper.map(newRecord,TimeDto.class);
    }

}
