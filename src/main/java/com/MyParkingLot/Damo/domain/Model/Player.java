package com.MyParkingLot.Damo.domain.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Player {
    @Id
    private Integer playerId = 1;
    private int balance;

    @OneToMany(mappedBy = "player")
    private List<ParkingLot> ownedParkingLots = new ArrayList<>();
}

/*
*Player（玩家）

balance（金錢）

ownedParkingLots（擁有的停車場）

upgrades（擴充裝備）
*
* */
