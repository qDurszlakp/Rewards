package com.example.rewards.controller;

import com.example.rewards.dto.TransactionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("/transactions")
public interface TransactionsApi {

    @Operation(summary = "Get all rewards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok")
    })
    @GetMapping(produces="application/json")
    ResponseEntity<List<TransactionDto>> getTransactions();

    @Operation(summary = "Make a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping(consumes="application/json")
    ResponseEntity<Void> storeTransaction(@RequestBody TransactionDto transactionDto);

    @Operation(summary = "Delete a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Resource deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @DeleteMapping()
    ResponseEntity<Void> deleteTransactions(
            @ParameterObject String customerFirstName,
            @ParameterObject String customerSecondName
    );

    @Operation(summary = "Update a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resource updated successfully"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @PutMapping()
    ResponseEntity<Void> updateTransactionAmount(
            @ParameterObject Long transactionId,
            @ParameterObject BigDecimal amount
    );

}
