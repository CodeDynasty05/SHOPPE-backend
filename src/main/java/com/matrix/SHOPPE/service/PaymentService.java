package com.matrix.SHOPPE.service;

import com.matrix.SHOPPE.model.dto.TransactionDto;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    TransactionDto createPayment(Double totalAmount, String accountNumber, String username, String password);

    TransactionDto confirmPayment(Integer id);
}
