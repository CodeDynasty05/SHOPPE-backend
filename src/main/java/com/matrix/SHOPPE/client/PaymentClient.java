package com.matrix.SHOPPE.client;

import com.matrix.SHOPPE.model.dto.TransactionAddRequestDto;
import com.matrix.SHOPPE.model.dto.TransactionDto;
import com.matrix.SHOPPE.model.dto.TransactionStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "payment-service", url = "http://localhost:8081")
public interface PaymentClient {

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    TransactionDto createPayment(@RequestBody TransactionAddRequestDto transactionAddRequestDto);

    @PutMapping("/transactions/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    TransactionDto confirmPayment(@PathVariable Integer transactionId, @RequestParam TransactionStatus transactionStatus);
}
