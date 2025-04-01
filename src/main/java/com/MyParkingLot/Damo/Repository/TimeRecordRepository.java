package com.MyParkingLot.Damo.Repository;

import com.MyParkingLot.Damo.Model.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRecordRepository extends JpaRepository<TimeRecord,Integer> {
}
