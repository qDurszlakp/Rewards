package com.example.rewards.service;

import com.example.rewards.domain.data.MonthYear;
import com.example.rewards.domain.data.RewardSummaries;
import com.example.rewards.entity.Customer;
import com.example.rewards.repository.TransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private TransactionJpaRepository transactionJpaRepository;

    @Value("${rewards.first.threshold}")
    private Long firstThreshold;

    @Value("${rewards.second.threshold}")
    private Long secondThreshold;

    @Value("${rewards.first.multiplier}")
    private Long firstMultiplier;

    @Value("${rewards.second.multiplier}")
    private Long secondMultiplier;

    @Override
    public RewardSummaries calculateRewardPoints() {

        Map<Customer, Map<MonthYear, Long>> summaries = new HashMap<>();

        transactionJpaRepository.findAll().forEach(transaction -> {
            val customer = transaction.getCustomer();

            summaries.putIfAbsent(customer, new HashMap<>());

            val monthYear = new MonthYear(
                    transaction.getTransactionDate().getMonth(),
                    transaction.getTransactionDate().getYear()
            );

            summaries.get(customer).putIfAbsent(monthYear, 0L);

            var currentRewardPoints = summaries.get(customer).get(monthYear);
            summaries.get(customer).put(monthYear, currentRewardPoints + calculatePoints(transaction.getAmount()));

        });

        return new RewardSummaries(summaries);
    }

    private Long calculatePoints(BigDecimal amount) {

        long output = 0L;

        if (amount.compareTo(new BigDecimal(firstThreshold)) > 0) {
            output += firstMultiplier * Math.min(amount.longValue() - firstThreshold, firstThreshold);
        }

        if (amount.compareTo(new BigDecimal(secondThreshold)) > 0) {
            output += secondMultiplier * (amount.longValue() - secondThreshold);
        }

        return output;
    }
}
