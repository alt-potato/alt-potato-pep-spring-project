package com.example.service;

import java.util.List;

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

    /**
     * Gets a List of all messages in the database.
     * 
     * @return a List<Message> containing all messages in the database, which may be empty
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Gets a specific message by its messageId
     * 
     * @param id The messageId to search for.
     * @return the Message with the given messageId, or null if not found
     */
    public Message getMessage(int id) {
        return messageRepository.findById(id).orElse(null);
    }
}
