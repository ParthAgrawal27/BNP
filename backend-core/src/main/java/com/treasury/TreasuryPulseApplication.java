package com.treasury;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TreasuryPulseApplication {
    public static void main(String[] args) {
        SpringApplication.run(TreasuryPulseApplication.class, args);
    }
}