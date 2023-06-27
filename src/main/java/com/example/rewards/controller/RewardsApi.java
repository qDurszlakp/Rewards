package com.example.rewards.controller;

import com.example.rewards.dto.RewardSummariesDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/rewards")
public interface RewardsApi {

    @Operation(summary = "Calculate reward points for every customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok")
    })
    @GetMapping(produces="application/json")
    ResponseEntity<RewardSummariesDto> calculateRewardPoints();

}
