package com.example.rewards.mapper;

import com.example.rewards.domain.data.RewardSummaries;
import com.example.rewards.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class RewardMapper {

    private final CustomerMapper customerMapper;

    public RewardSummariesDto map(RewardSummaries rewardSummaries) {

        List<CustomerSummaryDto> customerSummaries = new ArrayList<>();
        List<MonthYearSummaryDto> monthYearSummaryDtos = new ArrayList<>();
        AtomicReference<Long> totalRewardPoints = new AtomicReference<>(0L);

        rewardSummaries.getSummaries().forEach((customer, monthYearLongMap) -> {

            val customerDto = customerMapper.map(customer);

            monthYearSummaryDtos.clear();
            monthYearLongMap.forEach((k, v) -> {
                totalRewardPoints.updateAndGet(v1 -> v1 + v);
                monthYearSummaryDtos.add(new MonthYearSummaryDto(k, v));
            });

            customerSummaries.add(new CustomerSummaryDto(customerDto, monthYearSummaryDtos, totalRewardPoints.get()));
        });

        return new RewardSummariesDto(customerSummaries);
    }

}
