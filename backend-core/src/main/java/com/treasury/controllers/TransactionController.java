package com.treasury.controllers;

import com.treasury.services.CsvParserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final CsvParserService csvParserService;

    // Manual constructor replacing @RequiredArgsConstructor
    public TransactionController(CsvParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadTransactions(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a CSV file");
        }
        try {
            csvParserService.parseAndSaveTransactions(file);
            return ResponseEntity.ok("Transactions successfully uploaded and parsed.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to process file: " + e.getMessage());
        }
    }
}