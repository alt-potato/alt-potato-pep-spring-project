package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

/**
 * Account:
 *   accountId integer primary key auto_increment,
 *   username varchar(255) not null unique,
 *   password varchar(255)
```
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {
    /**
     * This query will return the Account that matches a certain given username String.
     * 
     * @param username The username to search for.
     * @return an Optional coutaining the Account that matches the given username, if it exists
     */
    Optional<Account> findAccountByUsername(String username);

    /**
     * This query will return the Account that matches a certain given username String and password String.
     * 
     * @param username The username to search for.
     * @param password The password to match.
     * @return an Optional coutaining the Account that matches the given username, if it exists
     */
    Optional<Account> findAccountByUsernameAndPassword(String username, String password);
}
