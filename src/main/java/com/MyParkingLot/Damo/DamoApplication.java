package com.MyParkingLot.Damo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DamoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DamoApplication.class, args);
	}

}

