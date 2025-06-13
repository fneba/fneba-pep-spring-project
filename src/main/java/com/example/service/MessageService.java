package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.*;
import java.util.Optional;
import com.example.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            return null;
        }
        if (!accountRepository.existsById(message.getPostedBy())) {
            return null;
        }
        return messageRepository.save(message);
    }

    public List<Message> getMessages(){
        return messageRepository.findAll();
    }

    public Message findMessageById(int messageId) {
        Optional<Message> optional = messageRepository.findById(messageId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    public boolean deleteMessageById(int messageId){
        if (messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return true;
        }
        return false;
    }

    public boolean editMessageById(int messageId, String messageText){
        if(!messageRepository.existsById(messageId) || messageText.isBlank() || messageText.length() > 255){
            return false;
        }
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessageText(messageText);
            messageRepository.save(message);
            return true;
        }

        return false;
    }

    public List<Message> getMessagesByAccountId(Integer accountId){
        return messageRepository.findByPostedBy(accountId);
    }

}
