package com.example.rewards.service;

import com.example.rewards.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions();

    void storeTransaction(Transaction transaction);

    void deleteTransactions(String customerFirstName, String customerSecondName);

    void updateTransactionAmount(Long transactionId, BigDecimal amount);
}
