package com.cicd.service;

import com.cicd.enumeration.TransactionType;
import com.cicd.model.BankAccount;
import com.cicd.model.Transaction;
import com.cicd.repository.BankAccountRepository;
import com.cicd.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BankService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public String credit(Long accountId, double amount) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        bankAccountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.CREDIT);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setBankAccount(account);
        transactionRepository.save(transaction);

        return "Amount credited successfully!";
    }

    @Transactional
    public String withdraw(Long accountId, double amount) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        bankAccountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setBankAccount(account);
        transactionRepository.save(transaction);

        return "Amount withdrawn successfully!";
    }
}
