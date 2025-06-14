package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.*;
import com.example.service.*;
import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // create a new Account on the endpoint POST localhost:8080/register
    @PostMapping(value = "/register")
    public ResponseEntity<Account> postAccount(@RequestBody Account account){
        Account newAccount = accountService.createAccount(account);
        return ResponseEntity.ok(newAccount);
    }

    // verify my login on the endpoint POST localhost:8080/login
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginAccount(@RequestBody Account loginRequest){
        Account account = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(account);
    }

    // submit a new post on the endpoint POST localhost:8080/messages
    @PostMapping(value = "/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message){
        Message newMessage = messageService.createMessage(message);
        if (newMessage == null){
            return ResponseEntity.status(400).build();
        } 
        return ResponseEntity.ok(newMessage);
    }

    // submit a GET request on the endpoint GET localhost:8080/messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageService.getMessages();
        return ResponseEntity.ok(messages);
    }

    // submit a GET request on the endpoint GET localhost:8080/messages/{messageId}
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        Message message = messageService.findMessageById(messageId);
        return ResponseEntity.ok(message);
    }

    // submit a DELETE request on the endpoint DELETE localhost:8080/messages/{messageId}
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> delMessageById(@PathVariable int messageId){
        boolean deleted = messageService.deleteMessageById(messageId);
        if (deleted) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    // submit a PATCH request on the endpoint PATCH localhost:8080/messages/{messageId}
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> patchMessage(@PathVariable int messageId, @RequestBody Message messageText){
        boolean updated = messageService.editMessageById(messageId, messageText.getMessageText());
        if (updated){
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.status(400).build();
        } 
    }

    // submit a GET request on the endpoint GET localhost:8080/accounts/{accountId}/messages
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable Integer accountId){
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.ok().body(messages);
    }


}
