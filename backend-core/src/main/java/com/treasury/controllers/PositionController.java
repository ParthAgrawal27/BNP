package com.treasury.controllers;

import com.treasury.services.PositionCalculatorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/positions")
@CrossOrigin(origins = "*")
public class PositionController {

    private final PositionCalculatorService positionService;

    // Manual constructor replacing @RequiredArgsConstructor
    public PositionController(PositionCalculatorService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<Map<String, Object>> getCalculatedPosition(
            @RequestParam String entity,
            @RequestParam String currency,
            @RequestParam(required = false, defaultValue = "0") BigDecimal previousBalance,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        LocalDate evaluationDate = (date != null) ? date : LocalDate.now();
        Map<String, Object> result = positionService.calculatePositionAndGap(entity, currency, previousBalance, evaluationDate);
        return ResponseEntity.ok(result);
    }
}