package com.example.rewards.mapper;

import com.example.rewards.dto.TransactionDto;
import com.example.rewards.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "customerFirstName", source = "customer.firstName")
    @Mapping(target = "customerSecondName", source = "customer.secondName")
    TransactionDto map(Transaction transaction);

    List<TransactionDto> mapTransactions(List<Transaction> transactions);

    @Mapping(target = "customer.firstName", source = "customerFirstName")
    @Mapping(target = "customer.secondName", source = "customerSecondName")
    Transaction map(TransactionDto transactionDto);

    List<Transaction> mapTransactionDtos(List<TransactionDto> transactionDtos);
}
