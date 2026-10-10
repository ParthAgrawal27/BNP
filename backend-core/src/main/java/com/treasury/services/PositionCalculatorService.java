package com.treasury.services;

import com.treasury.models.AlertThreshold;
import com.treasury.repositories.AlertThresholdRepository;
import com.treasury.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class PositionCalculatorService {

    private final TransactionRepository transactionRepository;
    private final AlertThresholdRepository thresholdRepository;

    // Manual constructor replacing @RequiredArgsConstructor
    public PositionCalculatorService(TransactionRepository transactionRepository, AlertThresholdRepository thresholdRepository) {
        this.transactionRepository = transactionRepository;
        this.thresholdRepository = thresholdRepository;
    }

    public Map<String, Object> calculatePositionAndGap(String entity, String currency, BigDecimal previousBalance, LocalDate date) {
        BigDecimal prev = previousBalance != null ? previousBalance : BigDecimal.ZERO;

        BigDecimal inflows = transactionRepository.calculateTotalInflows(entity, currency, date);
        if (inflows == null) inflows = BigDecimal.ZERO;

        BigDecimal outflows = transactionRepository.calculateTotalOutflows(entity, currency, date);
        if (outflows == null) outflows = BigDecimal.ZERO;

        BigDecimal projectedPosition = prev.add(inflows).subtract(outflows);

        BigDecimal threshold = thresholdRepository.findByEntityAndCurrency(entity, currency)
                .map(AlertThreshold::getThresholdAmount)
                .orElse(BigDecimal.valueOf(50000));

        BigDecimal gap = projectedPosition.subtract(threshold);

        String alertLevel = "NORMAL";
        boolean isSurplus = false;

        if (projectedPosition.compareTo(BigDecimal.ZERO) < 0 || projectedPosition.compareTo(threshold) < 0) {
            alertLevel = "EMERGENCY";
        } else {
            BigDecimal bufferPercentage = projectedPosition.subtract(threshold)
                    .divide(threshold, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));

            if (bufferPercentage.compareTo(BigDecimal.valueOf(5)) <= 0) {
                alertLevel = "CRITICAL";
            } else if (bufferPercentage.compareTo(BigDecimal.valueOf(10)) <= 0) {
                alertLevel = "WARNING";
            } else if (bufferPercentage.compareTo(BigDecimal.valueOf(20)) >= 0) {
                isSurplus = true;
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("entity", entity);
        response.put("currency", currency);
        response.put("previousBalance", prev);
        response.put("totalInflows", inflows);
        response.put("totalOutflows", outflows);
        response.put("projectedPosition", projectedPosition);
        response.put("threshold", threshold);
        response.put("liquidityGap", gap);
        response.put("status", gap.compareTo(BigDecimal.ZERO) >= 0 ? "SURPLUS" : "SHORTFALL");
        response.put("alertLevel", alertLevel);
        response.put("isDeployableSurplus", isSurplus);

        return response;
    }
}