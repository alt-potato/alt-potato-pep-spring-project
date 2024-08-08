package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.AccountNotFoundException;
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
     * @return the Account that matches the given username
     * @throws AccountNotFoundException
     */
    public Account findAccount(String username) throws AccountNotFoundException {
        return accountRepository.findAccountByUsername(username).orElseThrow(() -> new AccountNotFoundException());
    }

    /**
     * Return the Account that matches a certain given username String.
     * 
     * @param username The username to search for.
     * @return the Account that matches the given username
     * @throws AccountNotFoundException
     */
    public Account findAccount(int id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException());
    }

    /**
     * Log in a 
     * 
     * @param account
     * @return
     */
    public Account loginAccount(Account account) {
        return accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
