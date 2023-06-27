package com.example.rewards;

import com.example.rewards.dto.RewardSummariesDto;
import com.example.rewards.dto.TransactionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.hc.core5.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = RewardsApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:test.properties")
class RewardsApplicationIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void basicRewardsTest() {

        // when & then
        var contentAsString = mvc.perform(get("http://localhost:8080/rewards"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        val rewardSummariesDto_1 = objectMapper.readValue(contentAsString, RewardSummariesDto.class);

        assertThat(rewardSummariesDto_1).isNotNull();
        assertThat(rewardSummariesDto_1.getCustomerSummaries()).isEmpty();

        // given
        TransactionDto transactionDto1 = TransactionDto.builder()
                .amount(new BigDecimal("120.0"))
                .transactionDate(LocalDateTime.of(2022, 1, 1, 10, 0, 0))
                .customerFirstName("Khris")
                .customerSecondName("Khan")
                .build();

        TransactionDto transactionDto2 = TransactionDto.builder()
                .amount(new BigDecimal("120.0"))
                .transactionDate(LocalDateTime.of(2022, 1, 2, 10, 0, 0))
                .customerFirstName("Khris")
                .customerSecondName("Khan")
                .build();

        TransactionDto transactionDto3 = TransactionDto.builder()
                .amount(new BigDecimal("120.0"))
                .transactionDate(LocalDateTime.of(2022, 2, 1, 10, 0, 0))
                .customerFirstName("Khris")
                .customerSecondName("Khan")
                .build();

        // when
        mvc.perform(post("http://localhost:8080/transactions")
                        .contentType(ContentType.APPLICATION_JSON.getMimeType())
                        .content(objectMapper.writeValueAsString(transactionDto1)))
                .andExpect(status().isOk());
        mvc.perform(post("http://localhost:8080/transactions")
                        .contentType(ContentType.APPLICATION_JSON.getMimeType())
                        .content(objectMapper.writeValueAsString(transactionDto2)))
                .andExpect(status().isOk());
        mvc.perform(post("http://localhost:8080/transactions")
                        .contentType(ContentType.APPLICATION_JSON.getMimeType())
                        .content(objectMapper.writeValueAsString(transactionDto3)))
                .andExpect(status().isOk());

        // then
        contentAsString = mvc.perform(get("http://localhost:8080/rewards"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        val rewardSummariesDto_2 = objectMapper.readValue(contentAsString, RewardSummariesDto.class);

        assertThat(rewardSummariesDto_2).isNotNull();
        assertThat(rewardSummariesDto_2.getCustomerSummaries()).hasSize(1);
        assertThat(rewardSummariesDto_2.getCustomerSummaries().get(0).getTotalRewardPoints()).isEqualTo(270L);
        assertThat(rewardSummariesDto_2.getCustomerSummaries().get(0).getMonthYearSummary().get(0).getMonthYear().getMonth()).isIn(Month.JANUARY, Month.FEBRUARY);
        assertThat(rewardSummariesDto_2.getCustomerSummaries().get(0).getMonthYearSummary().get(0).getRewardPoints()).isIn(180L, 90L);
        assertThat(rewardSummariesDto_2.getCustomerSummaries().get(0).getMonthYearSummary().get(1).getMonthYear().getMonth()).isIn(Month.JANUARY, Month.FEBRUARY);
        assertThat(rewardSummariesDto_2.getCustomerSummaries().get(0).getMonthYearSummary().get(1).getRewardPoints()).isIn(180L, 90L);
    }

}
