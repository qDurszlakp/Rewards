package com.example.rewards.repository;

import com.example.rewards.entity.Customer;
import com.example.rewards.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAll();

    int deleteAllByCustomer(Customer customer);

}
