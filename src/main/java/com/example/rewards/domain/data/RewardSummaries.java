package com.example.rewards.domain.data;

import com.example.rewards.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class RewardSummaries {
    Map<Customer, Map<MonthYear, Long>> summaries;
}
