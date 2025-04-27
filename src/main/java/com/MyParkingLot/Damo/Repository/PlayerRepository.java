package com.MyParkingLot.Damo.Repository;

import com.MyParkingLot.Damo.domain.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player,Integer> {
}
