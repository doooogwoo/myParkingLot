package com.MyParkingLot.Damo.domain.Model;

import jakarta.persistence.CascadeType;
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
    private String playerName;
    private int balance;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingLot> ownedParkingLots = new ArrayList<>();
}

/*
upgrades（擴充裝備）
*
* */
