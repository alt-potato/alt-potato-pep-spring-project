package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;

    /**
     * POST /register
     * 
     * Create a new Account. The body will contain a representation of a JSON Account, but will not contain an accountId.
     * 
     * The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, 
     * and an Account with that username does not already exist. If all these conditions are met, the response body should contain 
     * a JSON of the Account, including its accountId. The response status should be 200 OK, which is the default. The new account 
     * should be persisted to the database.
     * If the registration is not successful due to a duplicate username, the response status should be 409. (Conflict)
     * If the registration is not successful for some other reason, the response status should be 400. (Client error)
     * 
     * @param account The account to register
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        // reject if username is blank
        if (account.getUsername().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // reject if password is less than 4 characters long
        if (account.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // no error message :(
        }
        // reject if username already exists
        if (accountService.findAccount(account.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(accountService.registerAccount(account));
    }

    /**
     * POST /login
     * 
     * Log in an account. The request body will contain a JSON representation of an Account.
     * 
     * The login will be successful if and only if the username and password provided in the request body JSON match a 
     * real account existing on the database. If successful, the response body should contain a JSON of the account in 
     * the response body, including its accountId. The response status should be 200 OK, which is the default.
     * If the login is not successful, the response status should be 401. (Unauthorized)
     * 
     * @param account The account to log in, which does not need to include an accountId.
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account oAccount = accountService.loginAccount(account);

        if (oAccount != null) {
            // account logged in!
            return ResponseEntity.status(HttpStatus.OK).body(oAccount);
        } else {
            // login failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
