package com.practise.Simple_Bank_App.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practise.Simple_Bank_App.entity.Account;
import com.practise.Simple_Bank_App.entity.Transaction;
import com.practise.Simple_Bank_App.repo.AccountRepo;
import com.practise.Simple_Bank_App.repo.TransactionRepo;

@Service
public class BankService {
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	// method to create a new account
	
	public Account createAccount(String holderName, double intialBalance) {
		 
		Account newAccount= new Account(holderName, intialBalance);
		
		return accountRepo.save(newAccount);
	}
	
	
	
	// method to retrieve all account
	
	public List<Account> getAllAccounts(){
		
		return accountRepo.findAll();
	}
	
	
	
	// method to deposit to an account
	
	
	public void deposit(Long accountId, double amount) {
		
		Account account =accountRepo.findById(accountId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid account Id"));
		
		account.setBalance(account.getBalance()+ amount);
		accountRepo.save(account);
		
		Transaction transaction=new Transaction("Deposit",amount,LocalDateTime.now(),account);
		transactionRepo.save(transaction);
		
	}
	
	
	
	// method to withdraw from an account
	
	public void withdraw(Long accountId, double amount) {
		
		Account account=accountRepo.findById(accountId)
				.orElseThrow(()-> new IllegalArgumentException("Invalid account Id"));
		
		if(account.getBalance()<amount) {
			throw new IllegalArgumentException("Insufficient Balance");
		}
		
		account.setBalance(account.getBalance()-amount);
		accountRepo.save(account);
		
		
		Transaction transaction=new Transaction("Withdraw",amount,LocalDateTime.now(),account);
		transactionRepo.save(transaction);
		
		
		
		
	}
	
	
	
	
	
	// method to get transactions history
	public List<Transaction> getTransactionHistory(Long accountId,String type,
			LocalDateTime startDate, LocalDateTime endDate){
		
		return transactionRepo.findByAccountIdAndOptionalFilters(accountId, type, startDate, endDate);
	}
	
	

}
