package com.practise.Simple_Bank_App.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practise.Simple_Bank_App.entity.Account;

public interface AccountRepo extends JpaRepository<Account, Long> {

}
