package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.client.PaymentClient;
import com.matrix.SHOPPE.exception.PaymentException;
import com.matrix.SHOPPE.model.dto.TransactionAddRequestDto;
import com.matrix.SHOPPE.model.dto.TransactionDto;
import com.matrix.SHOPPE.model.dto.TransactionStatus;
import com.matrix.SHOPPE.model.dto.TransactionType;
import com.matrix.SHOPPE.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentClient paymentClient;

    @Override
    public TransactionDto createPayment(Double totalAmount, String accountNumber, String username, String password) {
        log.info("Creating payment request");
        TransactionAddRequestDto transaction = TransactionAddRequestDto
                .builder()
                .amount(totalAmount)
                .accountNumber(accountNumber)
                .username(username)
                .transactionType(TransactionType.PURCHASE)
                .password(password)
                .build();
        log.info("Create payment response");
        return paymentClient.createPayment(transaction);
    }

    @Override
    public TransactionDto confirmPayment(Integer transactionId) {
        log.info("Confirming payment request");
        try {
            return paymentClient.confirmPayment(transactionId, TransactionStatus.SUCCESS);
        } catch (Exception e) {
            log.error("Error while confirming payment with id: {}", transactionId, e);
            throw new PaymentException("Error while confirming payment with id: " + transactionId);
        }
    }
}
