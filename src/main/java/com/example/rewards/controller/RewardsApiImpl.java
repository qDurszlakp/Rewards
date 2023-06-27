package com.example.rewards.controller;

import com.example.rewards.dto.RewardSummariesDto;
import com.example.rewards.mapper.RewardMapper;
import com.example.rewards.service.RewardService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RewardsApiImpl implements RewardsApi {

    private final RewardService rewardService;
    private final RewardMapper rewardMapper;

    @Override
    public ResponseEntity<RewardSummariesDto> calculateRewardPoints() {
        val rewardSummaries = rewardService.calculateRewardPoints();
        val rewardSummariesDto = rewardMapper.map(rewardSummaries);

        return ResponseEntity.ok(rewardSummariesDto);
    }
}
