package com.example.rewards.repository;

import com.example.rewards.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerJpaRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByFirstNameAndAndSecondName(String firstName, String secondName);

}
