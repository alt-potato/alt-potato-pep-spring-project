package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.*;
import com.example.exception.*;
import com.example.service.*;

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

    @Autowired
    MessageService messageService;

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
     * @throws javax.security.auth.login.AccountNotFoundException 
     */
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        // reject if username is blank
        if (account.getUsername().isBlank()) {
            throw new InvalidAccountException("Username cannot be blank.");
        }
        // reject if password is less than 4 characters long
        if (account.getPassword().length() < 4) {
            throw new InvalidAccountException("Password must be longer than 4 characters.");
        }
        // reject if username already exists
        try {
            // there's got to be a better way to do this
            accountService.findAccount(account.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);  
        } catch (AccountNotFoundException anfe) {
            ;
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
        try {
            Account oAccount = accountService.loginAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body(oAccount); 
        } catch (AccountNotFoundException anfe) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    /**
     * POST /messages
     * 
     * Submit a new post. The request body will contain a JSON representation of a message, which should be persisted to the 
     * database, but will not contain a messageId.
     * 
     * The creation of the message will be successful if and only if the messageText is not blank, is not over 255 characters, 
     * and postedBy refers to a real, existing user. If successful, the response body should contain a JSON of the message, 
     * including its messageId. The response status should be 200, which is the default. The new message should be persisted 
     * to the database.
     * If the creation of the message is not successful, the response status should be 400. (Client error)
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> submitMessage(@RequestBody Message message) {
        // reject if messageText is blank
        if (message.getMessageText().isBlank()) {
            throw new InvalidMessageException("Message cannot be blank.");
        }
        // reject if messageText is over 255 characters
        if (message.getMessageText().length() > 255) {
            // what is this, X, formerly Twitter? <- what is this, a reused joke?
            throw new InvalidMessageException("Message cannot exceed 255 characters long.");  
        }
        // reject if user does not exist
        accountService.findAccount(message.getPostedBy());  // throws if account not found

        return ResponseEntity.status(HttpStatus.OK).body(messageService.submitMessage(message));
    }

    /**
     * GET /messages
     * 
     * Get a list of all messages.
     * 
     * The response body should contain a JSON representation of a list containing all messages retrieved from the database. 
     * It is expected for the list to simply be empty if there are no messages. The response status should always be 200, 
     * which is the default.
     * 
     * @return
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages());
    }

    /**
     * GET /messages/{messageId}
     * 
     * Gets a specific message by its messageId.
     * 
     * The response body should contain a JSON representation of the message identified by the messageId. It is expected for 
     * the response body to simply be empty if there is no such message. The response status should always be 200, which 
     * is the default.
     * 
     * @param messageId The messageId of the message to find.
     * @return
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable int messageId) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessage(messageId));
    }

    /**
     * DELETE /messages/{messageId}.
     * 
     * Delete a specific message by its messageId.
     * 
     * The deletion of an existing message should remove an existing message from the database. If the message existed, the 
     * response body should contain the number of rows updated (1). The response status should be 200, which is the default.
     * If the message did not exist, the response status should be 200, but the response body should be empty. This is because 
     * the DELETE verb is intended to be idempotent, ie, multiple calls to the DELETE endpoint should respond with the same 
     * type of response.
     * 
     * @param messageId The messageId of the message to delete.
     * @return
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        int result = messageService.deleteMessage(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(result == 0 ? null : result);
    }



    /**
     * If an interaction is attempted with a nonexistent account, the handler will respond with the status 400 (Client error).
     */
    @ExceptionHandler({AccountNotFoundException.class})
    public ResponseEntity<String> handleAccountNotFound(AccountNotFoundException anfe) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account not found.");
    }

    /**
     * In the case of an invalid account/message access, the handler will respond with the status 400 (Client error).
     */
    @ExceptionHandler({InvalidAccountException.class, InvalidMessageException.class})
    public ResponseEntity<String> handleInvalidCreation(RuntimeException re) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
    }

    /**
     * A generalized handler for miscellaneous error cases.
     */
    // @ExceptionHandler({RuntimeException.class})
    // public ResponseEntity<String> handleMisc(RuntimeException re) {
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
    // }
}
