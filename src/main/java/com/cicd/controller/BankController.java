package com.cicd.controller;

import com.cicd.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank")
public class BankController {


    private final BankService bankService;

    BankController(BankService bankService){
        this.bankService = bankService;
    }

    @PostMapping("/credit/{accountId}")
    public ResponseEntity<String> credit(@PathVariable Long accountId, @RequestParam double amount) {
        return ResponseEntity.ok(bankService.credit(accountId, amount));
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<String> withdraw(@PathVariable Long accountId, @RequestParam double amount) {
        return ResponseEntity.ok(bankService.withdraw(accountId, amount));
    }
}
