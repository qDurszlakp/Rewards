package com.example.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSummaryDto {
    private CustomerDto customer;
    private List<MonthYearSummaryDto> monthYearSummary;
    private Long totalRewardPoints;
}
