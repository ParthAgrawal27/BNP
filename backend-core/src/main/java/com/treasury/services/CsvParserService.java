package com.treasury.services;

import com.treasury.models.Transaction;
import com.treasury.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class CsvParserService {

    private final TransactionRepository transactionRepository;

    // Manual constructor replacing @RequiredArgsConstructor
    public CsvParserService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void parseAndSaveTransactions(MultipartFile file) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<Transaction> csvToBean = new CsvToBeanBuilder<Transaction>(reader)
                    .withType(Transaction.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<Transaction> transactions = csvToBean.parse();
            transactionRepository.saveAll(transactions);
        }
    }
}