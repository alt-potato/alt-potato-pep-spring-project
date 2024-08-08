package com.example.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    /**
     * Registers (persists) an Account into the database.
     * 
     * @param account The Account without an accountId to register.
     * @return a registered Account object with the generated id
     */
    public Account registerAccount(Account account) {
        return accountRepository.save(account);  // "will never be null" 
    }

    /**
     * Return the Account that matches a certain given username String.
     * 
     * @param username The username to search for.
     * @return the Account that matches the given username, otherwise null
     */
    public Account findAccount(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    public Account loginAccount(Account account) {
        return accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
