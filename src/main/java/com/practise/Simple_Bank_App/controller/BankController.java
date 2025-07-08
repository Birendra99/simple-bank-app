package com.practise.Simple_Bank_App.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.practise.Simple_Bank_App.entity.Account;
import com.practise.Simple_Bank_App.entity.Transaction;
import com.practise.Simple_Bank_App.service.BankService;

@Controller
public class BankController {

    @Autowired
    private BankService bankService;

    // Home Page: Display all accounts
    @GetMapping("/")
    public String viewAllAccounts(Model model) {
        List<Account> accountList = bankService.getAllAccounts();
        model.addAttribute("accountList", accountList);
        return "Accounts";
    }

    // Create a new account
    @PostMapping("/account/add")
    public String createAccount(@RequestParam String holderName, @RequestParam double initialBalance) {
        bankService.createAccount(holderName, initialBalance);
        return "redirect:/";
    }

    // Deposit to account
    @PostMapping("/{id}/deposit")
    public String deposit(@PathVariable("id") Long accountId, @RequestParam double amount) {
        bankService.deposit(accountId, amount);
        return "redirect:/";
    }

    // Withdraw from account
    @PostMapping("/{id}/withdraw")
    public String withdraw(@PathVariable("id") Long accountId, @RequestParam double amount) {
        bankService.withdraw(accountId, amount);
        return "redirect:/";
    }

    // View transaction history
    @GetMapping("/{id}/transactions")
    public String viewTransactions(
            @PathVariable("id") Long accountId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            Model model) {
    	
    	if(startDate ==null) {
    		startDate= LocalDateTime.of(1900, 1,1,0,0,0,0);// default start date
    		
    	}
    	
    	if(endDate == null) {
    		endDate=LocalDateTime.of(9999, 12,31,23,59,59,999999);
    	}

        List<Transaction> transactions = bankService.getTransactionHistory(accountId, type, startDate, endDate);
        model.addAttribute("transactions", transactions);
        model.addAttribute("accountId", accountId);
        return "Transactions";
    }
}
