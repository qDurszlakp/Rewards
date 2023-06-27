package com.example.rewards.dto;

import com.example.rewards.domain.data.MonthYear;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthYearSummaryDto {
    private MonthYear monthYear;
    private Long rewardPoints;
}
