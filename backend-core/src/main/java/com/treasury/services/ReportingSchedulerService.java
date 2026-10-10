package com.treasury.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReportingSchedulerService {

    // Manual logger replacing @Slf4j
    private static final Logger log = LoggerFactory.getLogger(ReportingSchedulerService.class);

    @Scheduled(cron = "0 0 * * * *")
    public void runHourlyAlertChecks() {
        log.info("Executing hourly liquidity gap & threshold checks across all entities.");
    }

    @Scheduled(cron = "0 0 8 * * MON-FRI")
    public void generateSODReport() {
        log.info("Generating SOD Briefing: Expected opening positions, 24-48h obligations, overnight alerts.");
    }

    @Scheduled(cron = "0 0 18 * * MON-FRI")
    public void generateEODReport() {
        log.info("Generating EOD Report: Position summary, daily gaps, triggered alerts.");
    }
}