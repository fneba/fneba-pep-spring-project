package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.*;
import com.example.exception.*;
import java.util.Optional;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account){
        if (account.getUsername().isBlank() || account.getPassword().length() < 4){
            throw new ValidationException("Invalid username or password length");
        }
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new DuplicateUsernameException("Username already exists");
        }
        return accountRepository.save(account);
    }

    public Account login(String username, String password){
        Optional<Account> optAccount = accountRepository.findByUsername(username);
        
        if (optAccount.isPresent()) {
            Account account = optAccount.get();
            if (!account.getPassword().equals(password)) {
                throw new InvalidCredentialsException("Incorrect password");
            }
            return account;
        } else {
            throw new InvalidCredentialsException("User not found");
        }
    }

}
