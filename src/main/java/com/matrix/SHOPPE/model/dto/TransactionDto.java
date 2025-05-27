package com.matrix.SHOPPE.model.dto;

import lombok.Data;

@Data
public class TransactionDto {
    private Long id;
    private Double amount;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
}