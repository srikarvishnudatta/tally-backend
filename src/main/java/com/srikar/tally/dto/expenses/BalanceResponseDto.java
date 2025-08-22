package com.srikar.tally.dto.expenses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BalanceResponseDto {
    private Double income;
    private Double expenditure;
}
