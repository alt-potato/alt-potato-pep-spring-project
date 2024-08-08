package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    /**
     * Posts (persists) a new message to the database.
     * 
     * @param message The new Message without a messageId
     * @return a posted Message with a generated messageId
     */
    public Message submitMessage(Message message) {
        return messageRepository.save(message);
    }
}
