package com.example.rewards.service;

import com.example.rewards.entity.Customer;
import com.example.rewards.entity.Transaction;
import com.example.rewards.exception.CustomerCreationException;
import com.example.rewards.exception.CustomerNotFoundException;
import com.example.rewards.exception.TransactionNotFoundException;
import com.example.rewards.repository.CustomerJpaRepository;
import com.example.rewards.repository.TransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionJpaRepository transactionJpaRepository;
    private final CustomerJpaRepository customerJpaRepository;

    @Override
    public List<Transaction> getTransactions() {
        return transactionJpaRepository.findAll();
    }

    @Override
    @Transactional
    public void storeTransaction(Transaction transaction) {

        val optionalCustomer = checkCustomer(transaction);

        if (optionalCustomer.isPresent()) {
            transactionJpaRepository.save(
                    transaction.toBuilder()
                            .customer(optionalCustomer.get())
                            .build()
            );
        } else {
            transactionJpaRepository.save(transaction);
        }
    }

    @Override
    @Transactional
    public void deleteTransactions(String customerFirstName, String customerSecondName) {
        Optional<Customer> byFirstNameAndAndSecondName = customerJpaRepository.findByFirstNameAndAndSecondName(customerFirstName, customerSecondName);

        if (byFirstNameAndAndSecondName.isEmpty()) {
            log.info("Customer with provided details doesnt exists: [FirstName:{}, SecondName:{}]", customerFirstName, customerSecondName);
            throw new CustomerNotFoundException();
        }

        transactionJpaRepository.deleteAllByCustomer(byFirstNameAndAndSecondName.get());
    }

    @Override
    public void updateTransactionAmount(Long transactionId, BigDecimal amount) {
        Optional<Transaction> optionalTransaction = transactionJpaRepository.findById(transactionId);

        if (optionalTransaction.isEmpty()) {
            log.info("Customer with provided Id doesnt exists: [Id:{}]", transactionId);
            throw new TransactionNotFoundException();
        }

        val updatedTransaction = optionalTransaction.get().toBuilder().amount(amount).build();

        transactionJpaRepository.save(updatedTransaction);
    }

    private Optional<Customer> checkCustomer(Transaction transaction) {

        Customer customer = getCustomer(transaction);

        return customerJpaRepository.findByFirstNameAndAndSecondName(
                customer.getFirstName(),
                customer.getSecondName()
        );

    }

    private Customer getCustomer(Transaction transaction) {
        val firstName = Optional.of(transaction)
                .map(Transaction::getCustomer)
                .map(Customer::getFirstName)
                .orElseThrow(CustomerCreationException::new);

        val secondName = Optional.of(transaction)
                .map(Transaction::getCustomer)
                .map(Customer::getSecondName)
                .orElseThrow(CustomerCreationException::new);

        return new Customer(null, firstName, secondName, null);
    }
}
