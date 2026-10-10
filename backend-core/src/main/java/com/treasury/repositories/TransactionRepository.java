package com.treasury.repositories;

import com.treasury.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.entity = :entity AND t.currency = :currency AND t.direction = 'Inflow' AND t.transactionDate <= :date")
    BigDecimal calculateTotalInflows(@Param("entity") String entity, @Param("currency") String currency, @Param("date") LocalDate date);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.entity = :entity AND t.currency = :currency AND t.direction = 'Outflow' AND t.transactionDate <= :date")
    BigDecimal calculateTotalOutflows(@Param("entity") String entity, @Param("currency") String currency, @Param("date") LocalDate date);
}