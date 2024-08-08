package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

/**
 * Account:
 *   accountId integer primary key auto_increment,
 *   username varchar(255) not null unique,
 *   password varchar(255)
```
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * This query will return the Account that matches a certain given username String.
     * 
     * @param username The username to search for.
     * @return the Account that matches the given username, otherwise null (hopefully)
     */
    Account findAccountByUsername(String username);
}
