package com.example.rewards.controller;

import com.example.rewards.dto.TransactionDto;
import com.example.rewards.mapper.TransactionMapper;
import com.example.rewards.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionsApiImpl implements TransactionsApi {

    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;

    @Override
    public ResponseEntity<List<TransactionDto>> getTransactions() {

        val transactions = transactionService.getTransactions();
        val transactionDtos = transactionMapper.mapTransactions(transactions);

        return ResponseEntity.ok(transactionDtos);
    }

    @Override
    public ResponseEntity<Void> storeTransaction(TransactionDto transactionDto) {

        val transaction = transactionMapper.map(transactionDto);
        transactionService.storeTransaction(transaction);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteTransactions(String customerFirstName, String customerSecondName) {
        transactionService.deleteTransactions(customerFirstName, customerSecondName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> updateTransactionAmount(Long transactionId, BigDecimal amount) {
        transactionService.updateTransactionAmount(transactionId, amount);
        return ResponseEntity.ok().build();
    }

}
