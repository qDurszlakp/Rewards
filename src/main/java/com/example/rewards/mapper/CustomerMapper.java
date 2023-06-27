package com.example.rewards.mapper;

import com.example.rewards.dto.CustomerDto;
import com.example.rewards.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto map(Customer customer);

}
